package com.time.scenery.rain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 
	 * @Title: byte2hex 
	 * @Description: byte数组转16进制字符串
	 * @param buffer
	 * @return String
	 * @author Suqh
	 * @date 2016年9月1日
	 * @throws
	 */
	public static String byte2hex(byte [] buffer){  
        String h = "";      
        for(int i = 0; i < buffer.length; i++){  
            String temp = Integer.toHexString(buffer[i] & 0xFF);  
            if(temp.length() == 1){  
                temp = "0" + temp;  
            }  
            h = h + " "+ temp;  
        }  
        h=h.toUpperCase();
        return h.trim();    
    } 
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString=hexString.replace(" ", "");
	    hexString=hexString.replace(",", "");
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	} 
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	 /**
	  * 
	  * @Title: byteMerger 
	  * @Description:合并两个byte数组  
	  * @param byte_1
	  * @param byte_2
	  * @return byte[]
	  * @author Suqh
	  * @date 2016年9月1日
	  * @throws
	  */
     public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
    	 if (byte_1==null) {
    		 if (byte_2==null) {
				return null;
			}
			return byte_2;
		 }
    	 if (byte_2==null) {
			return byte_1;
		 }
         byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
         System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
         System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
         return byte_3;  
     }  
	 
	 
	 
//	public static void main(String[] args) {
////	byte[] byteBug={(byte) 0x80,0x01,0x00,0x05,0x01,(byte) 0x88,0x76,0x43,0x16,0x60,0x00,0x0E,0x02,(byte) 0x81,0x02,0x00,0x00};
//	String aString="7E 02 00 00 19 10 88 76 43 16 60 01 26 00 00 DE 00 00 00 01 00 00 E0 29 79 04 39 F7 25 00 02 01 15 12 17 09 54 17 3E 7E";
//	aString=aString.substring(2, aString.length()-6);
//	System.err.println(byte2hex(hexStringToBytes(aString)));
//	}
	public static byte[] my7e(String str) {  
		if (str.length()<7) {
			return null;
		}
		str=str.substring(2, str.length()-6);
		return hexStringToBytes(str);			
		} 
	
	

	 /*-----------------------------------
   笨方法：String s = "你要去除的字符串";
           1.去除空格：s = s.replace('\\s','');
           2.去除回车：s = s.replace('\n','');
   这样也可以把空格和回车去掉，其他也可以照这样做。
   注：\n 回车(\u000a) 
   \t 水平制表符(\u0009) 
   \s 空格(\u0008) 
   \r 换行(\u000d)*/
	
	
	
	/**
	 * 
	 * @Title: getStringContent
	 * @Description: 去掉字符串两边的"
	 * @author Wei.Wu
	 * @param str
	 * @return
	 * @throws
	 * @date 2014年8月22日 下午5:26:46
	 */
	public static String getStringContent(String str){
		Pattern p=Pattern.compile("^\"(.+)\"$");
		Matcher m=p.matcher(str);
		if(m.find()){
			str=m.group(1);
		}
		
		return str;
	}
	
	/**
	 * 
	 * @Title: getStringContent2
	 * @Description: 去掉字符串两边的'
	 * @author Wei.Wu
	 * @param str
	 * @return
	 * @throws
	 * @date 2015年1月7日 下午4:06:18
	 */
	public static String getStringContent2(String str){
		Pattern p=Pattern.compile("^\'(.+)\'$");
		Matcher m=p.matcher(str);
		if(m.find()){
			str=m.group(1);
		}
		
		return str;
	}
	/**
	 * 
	 * @Title: replaceBlank 
	 * @Description:去除字符串中的空格、回车、换行符、制表符 
	 * @param str
	 * @return String
	 * @author Suqh
	 * @date 2017年2月13日
	 * @throws
	 */
	public static String replaceBlank(String str) {
       String dest = "";
       if (str!=null) {
           Pattern p = Pattern.compile("\\s*|\t|\r|\n");
           Matcher m = p.matcher(str);
           dest = m.replaceAll("");
       }
       return dest;
   }
  
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static Boolean isNullOrBrank(String str){
		if (str==null || "".equals(str)) {
			return true;
		}
		return false;
	}

}
