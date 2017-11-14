package com.time.scenery.rain.utils;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XStreamUtils {

	private static final Logger logger = Logger.getLogger(XStreamUtils.class);

	private static XStream xstream;

	private static void init() {
		if (xstream == null) {
			xstream = new XStream(new StaxDriver(new XmlFriendlyNameCoder("_-", "_")));// 不需要XPP3库开始使用Java6
		}
	}

	public static XStream getXStream() {
		if (xstream == null) {
			xstream = new XStream(new StaxDriver(new XmlFriendlyNameCoder("_-", "_")));// 不需要XPP3库开始使用Java6
		}
		return xstream;
	}

	/**
	 * java bean 转化为xml文件
	 * 
	 * @param obj
	 * @return
	 */
	public static String toXml(Object obj) {
		String xmlStr = "";
		try {
			init();
			xmlStr = xstream.toXML(obj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("转化错误");
		}
		return xmlStr;
	}
}
