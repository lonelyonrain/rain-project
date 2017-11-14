package com.time.scenery.rain.util;

import java.rmi.server.UID;
import java.util.UUID;

public class UuidUtil {

	public static String getUUIDWithDelimiter(){
		return UUID.randomUUID().toString();
	}
	
	public static String getUUIDWithOutDelimiter(){
		return UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
	}
	
	public static String getUID(){
		return new UID().toString();
	}
}
