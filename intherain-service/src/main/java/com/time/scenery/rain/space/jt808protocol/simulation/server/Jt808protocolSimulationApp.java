package com.time.scenery.rain.space.jt808protocol.simulation.server;

import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import com.time.scenery.rain.log.Log;
import com.time.scenery.rain.space.jt808protocol.simulation.handler.ClinetIoHandler;
import com.time.scenery.rain.space.jt808protocol.simulation.message.JT808Message;
import com.xmgps.yun.communication.codec.P7E7EDecoder;
import com.xmgps.yun.communication.codec.P7E7EEncoder;
import com.xmgps.yun.communication.logger.DefaultSocketLogger;
import com.xmgps.yun.communication.logger.ReadWriteLoggingFilter;
import com.xmgps.yun.communication.message.MessageParseFilter;

/**
 *  
 * @ClassName: Jt808protocolSimulationApp 
 * @Description: 模拟车台小工具
 * @author suqh 
 * @date 2016年9月8日 下午8:02:46 
 *
 */
public class Jt808protocolSimulationApp {
	private static Logger log = Log.get("BackgroundServer"); 
	// 单例模式
	private static Jt808protocolSimulationApp g_obj = new Jt808protocolSimulationApp();
	public static Jt808protocolSimulationApp getInst() {
		return g_obj;
	}
	public IoSession setup() {			 
		//1.创建ioservice
		IoConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000);
		//2.注册过滤器
		ProtocolCodecFilter codecoder= new ProtocolCodecFilter(new P7E7EEncoder(), new P7E7EDecoder());
		connector.getFilterChain().addLast(codecoder.getClass().getName(),codecoder);
		// 线程池过滤器
		IoFilter executor = new ExecutorFilter(new OrderedThreadPoolExecutor());
		connector.getFilterChain().addAfter(codecoder.getClass().getName(), executor.getClass().getName(), executor);
		// 初始化网络数据日志记录器，只能初始化一次，网络数据日志记录器/过滤器
		IoFilter readWriteLogFilter =  new ReadWriteLoggingFilter(new DefaultSocketLogger("mycar"));
		connector.getFilterChain().addAfter(executor.getClass().getName(), readWriteLogFilter.getClass().getName(), readWriteLogFilter);
		//初始化消息解析过滤器，只能初始化一次，消息解析过滤器
		IoFilter msgParseFilter =new MessageParseFilter(new JT808Message());
		connector.getFilterChain().addAfter(readWriteLogFilter.getClass().getName(), msgParseFilter.getClass().getName(), msgParseFilter);
		//3注册iohandler,到ioserivce
		ClinetIoHandler clinetIoHandler=new ClinetIoHandler();
		connector.setHandler(clinetIoHandler);
		//4.绑定套接字,建立连接
//		ConnectFuture cf =connector.connect(new InetSocketAddress("218.5.80.8",45670));
		ConnectFuture cf =connector.connect(new InetSocketAddress("127.0.0.1",15602));
		
		Log.info(log,"45670:端口监听开始");
	        // 等待连接创建完成
	    cf.awaitUninterruptibly();
	    IoSession ioSession=cf.getSession();   
	    ioSession.getCloseFuture().awaitUninterruptibly();
	        // 释放连接
	    connector.dispose();
	    return ioSession;
	} 
}
