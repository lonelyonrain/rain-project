package com.time.scenery.rain.util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

public class Utility {
    /**
     * 获取指定文件名的URI。
     * 
     * @param name	文件名
     * @return		指定文件名的URI
     * @throws Exception 获取时发生异常
     */
	public static URI getResourceUri(String name) throws Exception {
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
	private static ClassLoader getTCL() 
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
     * 获取整数的十六进制字符串，格式为0x00000000
     */
	public static String getHexString(int value){
		StringBuffer sb = new StringBuffer();
		sb.append("0x");
		String msg = Integer.toHexString(value);
		for (int i = msg.length(); i < 8; i++){
			sb.append(0);
		}
		sb.append(msg);
		return sb.toString().toUpperCase();
	}
	
    /**
     * 获取字符串对应的整数值，该字符串可为十六进制（0x为前缀）或十进制
     */
	public static int getInt(String msgID){
		if (msgID == null) {
			return 0;
		}
		msgID = msgID.trim().toLowerCase();
		if (msgID.length() > 2 && msgID.startsWith("0x")) {
			return Integer.valueOf(msgID.substring(2), 16);
		} else {
			return Integer.valueOf(msgID);
		}
	}

}
