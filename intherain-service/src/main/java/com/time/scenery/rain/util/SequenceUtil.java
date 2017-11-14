package com.time.scenery.rain.util;

public class SequenceUtil {
	private static int totalCount = 0;  
	public static String getSequence() {
		if (totalCount>99999) {
			totalCount=0;
		}
		String res="00000";
		res=res+String.valueOf(totalCount);
		totalCount++;
		return res.substring(res.length()-5);
	}
/*	public static void main(String[] args) {
		System.err.println(SequenceUtil.getSequence());
		System.err.println(SequenceUtil.getSequence());
	}*/
}
