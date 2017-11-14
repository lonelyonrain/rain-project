package com.time.scenery.rain.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;


public class DES {
	private final static String DES = "DES";
    private final static String ENCODE = "GBK";
    private final static String defaultKey = "fjzlclpt";
    
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), defaultKey.getBytes(ENCODE));
//        String strs =  Encoder.encode(bt);
        byte[] res=  new Base64().encode(bt);
        String strs=new String(res); 
        return strs;
    }

    public static String decrypt(String data) throws IOException, Exception {
        if (data == null)
            return null;
//        BASE64Decoder decoder = new BASE64Decoder();
        Base64 base64 = new Base64();
        byte[] buf = base64.decode(data.getBytes());
        byte[] bt = decrypt(buf, defaultKey.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(ENCODE), key.getBytes(ENCODE));
        byte[] res=  new Base64().encode(bt);
        String strs=new String(res); 
        return strs;
    }

    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        Base64 base64 = new Base64();
        byte[] buf = base64.decode(data.getBytes());
        byte[] bt = decrypt(buf, key.getBytes(ENCODE));
        return new String(bt, ENCODE);
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
    
    
    public static void main(String[] args) throws Exception {
        File file=new File("C:/Users/RAIN/Desktop/家-省租赁车平台系统对接方案【地市交警支队】-201707/请求.xml");
        BufferedReader reader = null;
        String tempString = null;
        StringBuilder sb=new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
//            int line = 1;
            //1次读入一行，直到读入null为文件结速
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	sb.append(tempString);
//                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        System.out.println(encrypt(sb.toString()));
        System.out.println(decrypt(encrypt(sb.toString())));

    }
}
