package com.time.scenery.rain.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {
	// 在java程序开发中，ftp用的比较多，经常打交道，比如说向FTP服务器上传文件、下载文件，
	// 本文给大家介绍如何利用jakarta commons中的FTPClient（在commons-net包中）实现上传下载文件。
	/**
	 * Description: 向FTP服务器上传文件 @Version1.0 Jul 27, 2008 4:31:09 PM by
	 * 崔红保（cuihongbao@d-heaven.com）创建
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username, String password, String path, String filename,
			InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url,port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			ftp.setControlEncoding("iso-8859-1");//注意编码格式  
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.enterLocalPassiveMode();
			if (ftp.storeFile(new String(filename.getBytes("GBK"),"iso-8859-1"), input)) {
				input.close();
				ftp.logout();
				success = true;
			}else {
				input.close();
				ftp.logout();
				success = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * Description: 向FTP服务器上传文件 @Version1.0 Jul 27, 2008 4:31:09 PM by
	 * 崔红保（cuihongbao@d-heaven.com）创建
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
//	public static boolean uploadFile(String url, int port, String username, String password, String path,
//			String filename, InputStream input) {
//		boolean success = false;
//		FTPClient ftp = new FTPClient();
//		try {
//			int reply;
//			ftp.connect(url, port);// 连接FTP服务器
//			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
//			ftp.login(username, password);// 登录
//			reply = ftp.getReplyCode();
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				ftp.disconnect();
//				return success;
//			}
//			ftp.changeWorkingDirectory(path);
//			ftp.storeFile(filename, input);
//
//			input.close();
//			ftp.logout();
//			success = true;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (ftp.isConnected()) {
//				try {
//					ftp.disconnect();
//				} catch (IOException ioe) {
//				}
//			}
//		}
//		return success;
//	}

	/**
	 * Description: 从FTP服务器下载文件 @Version. Jul , :: PM by
	 * 崔红保（cuihongbao@d-heaven.com）创建
	 * 
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	// 1.将本地文件上传到FTP服务器上，代码如下：
	// @Test
	// publicvoid testUpLoadFromDisk() {
	// try {
	// FileInputStream in = new FileInputStream(new File("D:/test.txt"));
	// boolean flag = uploadFile("127.0.0.1", 21, "test", "test", "D:/ftp",
	// "test.txt", in);
	// System.out.println(flag);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @Test
	// public void testUpLoadFromDisk() {
	// try {
	// FileInputStream in = new FileInputStream(new File("D:/test.txt"));
	// boolean flag = uploadFile("127.0.0.1", 21, "test", "test", "D:/ftp",
	// "test.txt", in);
	// System.out.println(flag);
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// }

	// 2.在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
	// @Test
	// publicvoid testUpLoadFromString() {
	// try {
	// InputStream input = new ByteArrayInputStream("test
	// ftp".getBytes("utf-8"));
	// boolean flag = uploadFile("127.0.0.1", 21, "test", "test", "D:/ftp",
	// "test.txt", input);
	// System.out.println(flag);
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// }
	//
//public static void main(String[] args) {
//	 try {
//		 InputStream input = new ByteArrayInputStream("testftp".getBytes("utf-8"));
//		 boolean flag = uploadFile(GlobSerMan.map.get("ftpip"),Integer.valueOf(GlobSerMan.map.get("ftpport")), GlobSerMan.map.get("ftpname"), GlobSerMan.map.get("ftppassword"), "F:/myFtp","test.txt", input);
//		 System.out.println(flag);
//		 
//		 
//		 
//		 } catch (UnsupportedEncodingException e) {
//		 e.printStackTrace();
//		 }
//}
}
