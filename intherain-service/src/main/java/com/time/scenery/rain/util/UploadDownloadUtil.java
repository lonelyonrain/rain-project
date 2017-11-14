package com.time.scenery.rain.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;

import com.time.scenery.rain.log.Log;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class UploadDownloadUtil {
	// 日志
	private static Logger log = Log.get("BackgroundServer");
	/**
	 * 从共享目录拷贝文件到本地
	 * @param remoteUrl 共享目录上的文件路径
	 * @param localDir 本地目录
	 */
	@SuppressWarnings("unused")
	static void smbGet(String remoteUrl, String localDir)
	{
		InputStream in = null;
		OutputStream out = null;
		try{
			SmbFile remoteFile = new SmbFile(remoteUrl);
			//这一句很重要
			remoteFile.connect();
			if (remoteFile == null){
				Log.error(log,"文件不存在："+remoteUrl);
				return;
			}
			String fileName = remoteFile.getName();
			File localFile = new File(localDir + File.separator + fileName);
			in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1){
				out.write(buffer);
				buffer = new byte[1024];
			}
		}catch (Exception e){
			Log.error(log,"UploadDownloadUtil："+e);
		}finally{
			try{
				out.close();
				in.close();
			}catch (IOException e){
				Log.error(log,"UploadDownloadUtil："+e);
			}
		}
	}

	/**
	 * 从本地上传文件到共享目录
	 * @Version1.0 Sep 25, 2009 3:49:00 PM
	 * @param remoteUrl 共享文件目录
	 * @param localFilePath 本地文件绝对路径
	 */
	static void smbPut(String remoteUrl, String localFilePath)
	{
		InputStream in = null;
		OutputStream out = null;
		try{
			File localFile = new File(localFilePath);
			String fileName = localFile.getName();
			SmbFile remoteFile = new SmbFile(remoteUrl + "/" + fileName);
			in = new BufferedInputStream(new FileInputStream(localFile));
			out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1)
			{
				out.write(buffer);
				buffer = new byte[1024];
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				out.close();
				in.close();
			}catch (IOException e){
				Log.error(log,"UploadDownloadUtil："+e);
			}
		}
	}
	/**
	 * 
	 * @Title: smbPutPic 
	 * @Description: 上传图片到共享目录
	 * @param data
	 * @param filename 文件名
	 * @param remoteUrl 远程地址
	 * @return boolean
	 * @author Suqh
	 * @date 2016年12月23日
	 * @throws
	 */
	public static boolean smbPutPic (byte[] data,String filename,String remoteUrl){
		InputStream in = null;
		OutputStream out = null;
		try{
			SmbFile remotePaFile = new SmbFile(remoteUrl);
			 //如果文件夹不存在则创建    
		    if(!remotePaFile.exists()){       
		    	remotePaFile.mkdirs();    
		    }  
		    SmbFile	remoteFile = new SmbFile(remoteUrl+"/"+filename);
			if(remoteFile.exists()){       
				return true;  
			}  
			in = new ByteArrayInputStream(data);
			out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1){
				out.write(buffer);
				buffer = new byte[1024];
			}
			return true;
		}catch (Exception e){
			Log.error(log,"UploadDownloadUtil:"+remoteUrl+"/"+filename+"=="+e);
			return false;
		}finally{
			try{
				if (out!=null) {
					out.close();
				}
				if (in!=null) {
					in.close();
				}
			}catch (IOException e){
				Log.error(log,"UploadDownloadUtil:"+remoteUrl+"/"+filename+"=="+e);
				return false;
			}
		}
	}
	//实例
//	public static void main(String[] args)
//	{
//		UploadDownloadUtil test = new UploadDownloadUtil();
//		// smb:域名;用户名:密码@目的IP/文件夹/文件名.xxx  (域名可不要)
//	    test.smbGet("smb://szpcg;jiang.t:xxx@192.168.193.13/Jake/test.txt","c://") ;
//		test.smbPut("smb://szpcg;jiang.t:xxx@192.168.193.13/Jake","c://test.txt");
//		//用户名密码不能有强字符，也就是不能有特殊字符，否则会被作为分断处理
//		test.smbGet("smb://CHINA;xieruilin:123456Xrl@10.70.36.121/project/report/网上问题智能分析助手使用文档.doc","c://Temp/");
//	}
}
