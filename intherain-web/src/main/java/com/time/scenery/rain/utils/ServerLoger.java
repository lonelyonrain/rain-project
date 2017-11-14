package com.time.scenery.rain.utils;

import org.apache.log4j.Logger;

public abstract class ServerLoger {
	// 日志
	static final Logger logger = Logger.getLogger("ReportServer");

	public static void info(String format, Object... args) {
		if (null != logger && logger.isInfoEnabled()) {
			logger.info(String.format(format, args));
		}
	}

	public static void debug(String format, Object... args) {
		if (null != logger && logger.isDebugEnabled()) {
			logger.debug(String.format(format, args));
		}
	}

	public static void error(String format, Object... args) {
		if (null != logger) {
			logger.error(String.format(format, args));
		}
	}

	public static void error(Throwable t, String format, Object... args) {
		if (null != logger)
			logger.error(String.format(format, args), t);
	}
}
