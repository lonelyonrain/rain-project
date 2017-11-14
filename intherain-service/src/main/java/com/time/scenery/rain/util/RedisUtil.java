package com.time.scenery.rain.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.time.scenery.rain.log.Log;

/**
 * 
 * @author RAIN（独立文件）
 *
 */
public class RedisUtil {
	private static Logger log = Log.get("BackgroundServer");
	/**
     * Redis服务器的IP
     */
    private String ip;
    /**
     * Redis服务器的端口
     */
    private int port;
    /**
     * Redis服务器的访问密码,无则置为null
     */
    private String auth;
    /**
     * Redis的当前数据库序号,默认为0
     */
    private int dataBase = 0;
    /**
     * 控制一个jedisPool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
     */
	private int max_idle = 8;
	/**
	 * 超时设置
	 */
	private int timeout = 10000;
    /**
     *  服务器权重，默认为1
     */
    private int weight = 1;
    
//    private String redisVersion ="1";
	/**
	 * 私有构造函数（单例模式）
	 */
	private RedisUtil() {
	}
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;
	// Redis连接单例， 如未开启使用Redis，则返回null
	private volatile static RedisUtil _instance = null;
	// Redis服务器是否启用(默认开启)
	public  static boolean useRedis = true;
	/**
	 * @MethodsName: getInstance:
	 * @Description: 返回redis查询实例，如不启用redis，返回null
	 * @author: suqh
	 * @date: 2016年10月18日 上午9:55:06
	 * @since JDK 1.7
	 */
	public static RedisUtil getInstance() {
		if (!RedisUtil.useRedis) {
			return null;
		}
		if (_instance == null) {
			synchronized (RedisUtil.class) {
				if (_instance == null) {
					_instance=new RedisUtil();
					if (!_instance.loadConfig()) {
						Log.error(log,"Redis配置文件读取失败");
						return null;
					};
					if (!_instance.initialize()) {
						Log.error(log,"Redis服务器连接初始化失败！");
						return null;
					}
					Log.info(log,"RedisUtil启动成功...");
				}
			}
		}
		return _instance;
	}
	/**
     * 获取指定文件名的URI。
     * 
     * @param name	文件名
     * @return		指定文件名的URI
     * @throws Exception 获取时发生异常
     */
	public URI getResourceUri(String name) throws Exception {
		ClassLoader classLoader = getTCL();
		URL url = classLoader.getResource(name);
		return url == null ? null : url.toURI();
	}
	
	/**
	 * Get the Thread Context Loader which is a JDK 1.2 feature. If we are
	 * running under JDK 1.1 or anything else goes wrong the method returns
	 * <code>null<code>.
	 * 
	 * */
	private ClassLoader getTCL() 
			throws IllegalAccessException, InvocationTargetException {
		// Are we running on a JDK 1.2 or later system?
		Method method = null;
		try {
			method = Thread.class.getMethod("getContextClassLoader", (Class<?>[])null);
		} catch (NoSuchMethodException e) {
			// We are running on JDK 1.1
			return null;
		}
		 
		return (ClassLoader) method.invoke(Thread.currentThread(), (Object[])null);
	}
	/**
     * 加载配置文件
     * @return 加载成功则返回true，否则返回false
     */
//	StorageService.config
//	<RedisSection>
//    <Switch>true</Switch>
//    <Version>1</Version>
//    <!-- auth:redis访问密码,无则置空; database:redis指定数据库序号,默认为0 -->
//    <!-- <RedisConnection ip="10.50.1.135" port="6379" auth="98a7ca719c574386b79d5e6142765138" max_idle="8" timeout="10000" database="0" weight="1"/> --> 
//    <RedisConnection ip="10.5.16.82" port="6379" auth="xmgps" max_idle="8" timeout="10000" database="5" weight="1"/> 
//  </RedisSection>
	public boolean loadConfig(){
		try {
			File file = new File(getResourceUri("StorageService.config"));
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);
			Node node;
			// Redis服务器连接配置
			node = document.getRootElement().selectSingleNode("RedisSection");
			if(node != null){
				// 读取Redis服务器连接配置
				// 数据库配置版本号
				Node temp = node.selectSingleNode("Version");
				// 是否启用开关
				temp = node.selectSingleNode("Switch");
				if (temp != null) {
					useRedis = Boolean.parseBoolean(temp.getText());
					if (!useRedis) {
						Log.info(log,"Redis未开启！");
						return false;
					}
				}
				// 网络连接配置
				temp = node.selectSingleNode("RedisConnection");
				if(temp != null){
					RedisUtil.getInstance().valueOf(temp.asXML());
				}
			}
			return true;
		} catch (DocumentException e) {
			Log.error(log,e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.error(log,e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * @MethodsName: initialize:
	 * @param connList Redis服务器连接，其中权重信息将被忽略。
	 * @Description: 初始化Redis服务器连接,初始化成功，返回true；否则返回false
	 * @author: suqh
	 * @date: 2016年10月18日 上午9:54:17
	 * @since JDK 1.7
	 */
	public boolean initialize() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(_instance.max_idle);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, _instance.ip, _instance.port, _instance.timeout,
					_instance.auth, _instance.dataBase);
			Log.info(log,"RedisServer init success...");
			return true;
		} catch (Exception e) {
			Log.error(log,"初始化Redis服务器失败：" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @MethodsName: getJedis:
	 * @Description: 获取一个redis连接
	 * @author: suqh
	 * @date: 2016年10月18日 上午9:55:23
	 * @since JDK 1.7
	 */
	public synchronized Jedis getJedis() {
		try {
			if (jedisPool != null) {
				return jedisPool.getResource();
			}
		} catch (Exception e) {
			Log.error(log,"获取Redis连接异常...", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 有返回结果的回调接口定义。
	 */
	public interface JedisAction<T> {
		T action(Jedis jedis);
	}

	/**
	 * 无返回结果的回调接口定义。
	 */
	public interface JedisActionNoResult {
		void action(Jedis jedis);
	}
	
	/**
	 * 执行有返回结果的action。
	 */
	public <T> T execute(JedisAction<T> jedisAction) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getJedis();
			if (null != jedis) {
				return jedisAction.action(jedis);
			} else {
				Log.info(log, "jedis is no available...");
			}
		} catch (JedisConnectionException e) {
			broken = true;
			Log.error(log,"Redis connection lost.", e);
			e.printStackTrace();
		} catch (Exception e) {
			Log.error(log,"Redis Exception: ", e);
			e.printStackTrace();
		} finally {
			closeResource(jedis, broken);
		}
		return null;
	}

	/**
	 * 执行无返回结果的action。
	 */
	public void execute(JedisActionNoResult jedisAction) {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = getJedis();
			if (null != jedis) {
				jedisAction.action(jedis);
			} else {
				Log.info(log,"jedis is no available...");
			}
		} catch (JedisConnectionException e) {
			broken = true;
			Log.error(log,"Redis connection lost.", e);
			e.printStackTrace();
		} catch (Exception e) {
			Log.error(log,"Redis Exception: ", e);
			e.printStackTrace();
		} finally {
			closeResource(jedis, broken);
		}
	}
	
	/**
	 * 根据连接是否已中断的标志，分别调用returnBrokenResource或returnResource。
	 */
	@SuppressWarnings("deprecation")
	public static void closeResource(Jedis jedis, boolean connectionBroken) {
		if (jedis != null) {
			try {
				if (connectionBroken) {
					jedisPool.returnBrokenResource(jedis);
				} else {
					jedisPool.returnResource(jedis);
				}
			} catch (Exception e) {
				Log.error(log,"Error happen when return jedis to pool, try to close it directly.", e);
				closeJedis(jedis);
			}
		}
	}

	/**
	 * 关闭Jedis连接
	 */
	private static void closeJedis(Jedis jedis) {
		if ((jedis != null) && jedis.isConnected()) {
			try {
				try {
					jedis.quit();
				} catch (Exception e) {
				}
				jedis.disconnect();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * @Title: publishMsg   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param channel
	 * @param message
	 * @return      
	 * @return boolean      
	 * @throws   
	 * @date 2017年8月23日 上午8:41:29
	 * @auth suqh
	 */
	public boolean publishMsg(final String channel, final String message) {
		Boolean flag = execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				Log.info(log,"【消息发布】向Redis发布消息:channel: %s, msg: %s", channel, message);
				jedis.publish(channel, message);
				return true;
			}
		});
		return (null == flag ? false : flag);
	}

	/**
	 * 
	 * @Title: getBeanList   
	 * @Description: 将redis中key值对应的Json格式value转换为指定bean的集合   
	 * @param key
	 * @param cls
	 * @return      
	 * @return List<T>      
	 * @throws   
	 * @date 2017年8月23日 上午8:41:41
	 * @auth suqh
	 */
	public <T> List<T> getBeanList(final String key, final Class<T> cls) {
		return execute(new JedisAction<List<T>>() {
			@Override
			public List<T> action(Jedis jedis) {
				return JSON.parseArray(jedis.get(key), cls);
			}
		});
	}

	/**
	 * @MethodsName: getSingleBean:
	 * @Description: 将redis中key值对应的Json格式value转换为单个指定bean
	 * @author: suqh
	 * @date: 2016年10月18日 上午9:57:27
	 * @since JDK 1.7
	 */
	public <T> T getSingleBean(final String key, final Class<T> cls) {
		return execute(new JedisAction<T>() {
			@Override
			public T action(Jedis jedis) {
				return JSON.parseObject(jedis.get(key), cls);
			}
		});
	}

	/**
	 * @MethodsName: getValue:
	 * @Description: 从redis中获取指定key值的value，无数据则返回null
	 * @author: suqh
	 * @date: 2016年10月18日 上午10:08:20
	 * @since JDK 1.7
	 */
	public String getValue(final String key) {
		return execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	
	  /**
	 * @MethodsName: existsValue:
	 * @Description: 判断key是否存在
	 * @author: suqh
	 * @date: 2016年10月20日 下午3:27:51
	 * @since JDK 1.7
	 */
	public Boolean existsValue(final String key) {
		Boolean flag = execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.exists(key);
			}
		});
		return (null == flag ? false : flag);
	}
	
	
	/**
	 * @MethodsName: putValueByJson:
	 * @Description: 以JSON格式存储至redis
	 * @author: suqh
	 * @date: 2016年10月19日 上午9:59:20
	 * @since JDK 1.7
	 */
	public void putValueByJson(final String key, Object value) {
		final String jsonStr = JSON.toJSONString(value,
				SerializerFeature.DisableCircularReferenceDetect);
		putValue(key, jsonStr);
	}
	
	/**
	 * @MethodsName: putValue:
	 * @Description: 设置key-value置redis中
	 * @author: suqh
	 * @date: 2016年10月18日 上午10:24:41
	 * @since JDK 1.7
	 */
	public void putValue(final String key, final String value) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.set(key, value);
			}
		});
	}
	
	/**
	 * @MethodsName: putValueByJsonWithTTL:
	 * @Description: 以JSON格式存储至redis并设置超时时间
	 * @author: suqh
	 */
	public void putValueByJsonWithTTL(final String key, Object value, final int seconds){
		final String jsonStr = JSON.toJSONString(value,
				SerializerFeature.DisableCircularReferenceDetect);
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.setex(key, seconds, jsonStr);
			}
		});
	}
	
	/**
	 * @MethodsName: setExpire:
	 * @Description: 设置键值的超时时间
	 * @author: suqh
	 */
	public void setExpire(final String key, final int seconds){
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.expire(key, seconds);
			}
		});
	}

	/**
	 * @MethodsName: delValue:
	 * @Description: 从redis批量删除key
	 * @author: suqh
	 * @date: 2016年10月18日 上午11:07:27
	 * @since JDK 1.7
	 */
	public boolean delValue(final String... keys) {
		Boolean flag = execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.del(keys) == 1 ? true : false;
			}
		});
		return (null == flag ? false : flag);
	}

	/**
	 * @MethodsName: mgetStrMap:
	 * @Description: 使用mget方式批量获取String键值
	 * @author: suqh
	 * @date: 2016年11月2日 下午3:39:17
	 * @since JDK 1.7
	 */
	public Map<String, String> mgetStrMap(final String... keys) {
		Map<String, String> result = new HashMap<String, String>();
		List<String> mget = execute(new JedisAction<List<String>>() {
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
		if (null != mget) {
			for (int i = 0; i < keys.length; i++) {
                result.put(keys[i], mget.get(i));
            }
		}
		return result;
	}
	
	
	/**
	 * @MethodsName: mgetTMap:
	 * @Description: 使用mget方式批量获取指定类型键值
	 * @author: suqh
	 * @date: 2016年11月2日 下午4:33:47
	 * @since JDK 1.7
	 */
	public <T> Map<String, T> mgetTMap(Class<T> cls, final String... keys){
		List<String> resulList = execute(new JedisAction<List<String>>() {
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
		Map<String, T> result = null;
		if (null != resulList) {
			result = new HashMap<String, T>();
			for (int i = 0; i < keys.length; i++) {
                result.put(keys[i], JSON.parseObject(resulList.get(i), cls));
            }
		}
		return result;
	}

	/**
	 * @MethodsName: mgetTList:
	 * @Description: 使用mget方式批量获取指定类型值集合
	 * @author: suqh
	 * @date: 2016年11月2日 下午4:33:47
	 * @since JDK 1.7
	 */
	public <T> ArrayList<T> mgetTList(Class<T> cls, final String... keys){
		List<String> resulList = execute(new JedisAction<List<String>>() {
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
		ArrayList<T> result = null;
		if (null != resulList) {
			result = new ArrayList<T>();
			for (int i = 0; i < keys.length; i++) {
                result.add(JSON.parseObject(resulList.get(i), cls));
            }
		}
		return result;
	}
	//===========================
	 /**
     * 构造服务器连接对像实例
     * 
     * @param ip		远端会话IP
     * @param port		远端会话端口
     * @param auth     访问密码
     * @param dataBase 数据库序号
     * @param weight	服务器权重
     */
    public void redisConnection(String ip, int port, String auth, int max_idle, int timeout, int dataBase, int weight) {
    	this.ip = ip;
    	this.port = port;
    	this.auth = auth;
    	this.dataBase = dataBase;
    	this.max_idle = max_idle;
    	this.timeout = timeout;
    	this.weight = weight;
    }
    
    /**
     * 将RedisConnection转换为XML字符串
     * @return 转换后得到的XML字符串
     */
    public String toXmlString(){
    	return "<RedisConnection ip=\""  + ip + "\" port=\"" + port + "\" auth=\"" + auth 
    			+ "\" max_idle=\"" + max_idle + "\" timeout=\"" + timeout + "\" dataBase=\"" + dataBase + "\" weight=\"" + weight + "\"/>";
    }

    /**
     * 从XML字符串转换为RedisConnection
     * @param xml String RedisConnection字符串
     * @return 转换后得到的RedisConnection
     */
	public void valueOf(String xml) {
		String ip;
		int port;
		String auth;
		int max_idle;
		int timeout;
		int dataBase;
		int weight;
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile("<RedisConnection.*ip=\"([0-9.]+?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			ip = matcher.group(1);
		} else {
			return ;
		}

		pattern = Pattern.compile("<RedisConnection.*port=\"(\\d+?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			port = Integer.valueOf(matcher.group(1));
		} else {
			return ;
		}

		pattern = Pattern.compile("<RedisConnection.*auth=\"(.*?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			auth = matcher.group(1).trim();
			if (null == auth || "".equals(auth) || "null".equalsIgnoreCase(auth)) {
				auth = null;
			}
		} else {
			return ;
		}

		pattern = Pattern.compile("<RedisConnection.*max_idle=\"(\\d+?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			max_idle = Integer.valueOf(matcher.group(1));
		} else {
			return ;
		}

		pattern = Pattern.compile("<RedisConnection.*timeout=\"(\\d+?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			timeout = Integer.valueOf(matcher.group(1));
		} else {
			return ;
		}

		pattern = Pattern.compile("<RedisConnection.*database=\"(\\d+?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			dataBase = Integer.valueOf(matcher.group(1));
		} else {
			return ;
		}

		pattern = Pattern.compile("<RedisConnection.*weight=\"(\\d+?)\".*/>");
		matcher = pattern.matcher(xml);
		if (matcher.find()) {
			weight = Integer.valueOf(matcher.group(1));
		} else {
			return ;
		}
		RedisUtil.getInstance().redisConnection(ip, port, auth, max_idle, timeout, dataBase, weight);
	}
	
//	实例
//	if (null != body.getOrganization() && "TAXIAPP".equals(body.getOrganization()) && (driverData.getLoginstatus()==2 || driverData.getLoginstatus()==1)) {
//		RedisUtil redisUtil=RedisUtil.getInstance();
//		String car_key="GPSLastPosition:"+String.valueOf(carId);
//		if (redisUtil!=null && redisUtil.existsValue(car_key)) {
//			String str= redisUtil.getValue("GPSLastPosition:"+String.valueOf(carId));
//			LastGpsData track =JSON.parseObject(str,LastGpsData.class);  
//	        // 签到签退数据 add by suqh 20170725
//			track.setLoginstatus(driverData.getLoginstatus());	
//			track.setDriver(driverData.getDriver());
//			track.setLoginOutTimeStr(driverData.getLoginOutTimeStr());
//			track.setLicenseNo(driverData.getLicenseNo());
//			if (track.getLoginstatus()==0) {
//				track.setDriver(null);
//				track.setLoginOutTimeStr(null);
//				track.setLicenseNo(null);
//			}
//			redisUtil.putValueByJson(car_key, track);
//			return track;
//		}else {
//			//日志打印
//			if (redisUtil==null) {
//				if (RedisUtil.useRedis) {
//					logger.info("redis服务并未开启");															
//				}else {
//					logger.error("redis服务发生错误，请检查");	
//				}
//			}else {
//				logger.info("redis未找到该车辆末次位置信息");			
//			}
//		}
//}
}
