package com.time.scenery.rain.util;

import java.io.File;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.log4j.Logger;

public class ByteUtil {
	public static void byte2image(byte[] data,String path,Logger logger){
		if (null==path) {
			path="E:/myRoom/hotosfile/10.jpg";
		}
	      if(data.length<3||path.equals("")) {
	    	  logger.info("数据长度太小：" + path);
	    	  return;	    	  
	      }
	    	  
	      try{
	      FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	      imageOutput.write(data, 0, data.length);
	      imageOutput.flush();
	      imageOutput.close();
	      System.err.println("成功==>：" + path);
	      logger.info("成功：" + path);
	      } catch(Exception ex) {
	       System.err.println("Exception: " + ex);
	       ex.printStackTrace();
	       logger.error("Exception: " + ex);
	       logger.info("错误：" + path);
	    }
	 }
}
