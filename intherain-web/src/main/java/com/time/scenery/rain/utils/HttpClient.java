package com.time.scenery.rain.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/**
 * 
 * @ClassName: HttpClient 
 * @Description: HttpClient请求
 * @author suqh 
 * @date 2016年8月12日 下午3:27:58 
 *
 */
public class HttpClient { 
//	public static void main(String args[]) {
//		String url = "http://218.5.80.24:8003/taxi/passenger/ordercancel.xml";
//		String userName = "test";
//		String passWord = "1234";
//		String content = getHttp(userName, passWord, url);
//		System.out.println(content);
//		try {
//			System.out.println(XmlUtils.xml2Map(content).get("result").toString());
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//	}
   
	/**
	 * 
	 * @Title: getHttp 
	 * @Description: Http请求get方式
	 * @param userName
	 * @param passWord
	 * @param url
	 * @return String
	 * @author Suqh
	 * @date 2016年8月12日
	 * @throws
	 */
	public static String getHttp(String userName, String passWord, String url) {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// 设置认证
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		if (!XString.isNullOrBrank(userName) && !XString.isNullOrBrank(passWord)) {
			credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord));			
		}
		httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
		// HttpClient
		// httpClientBuilder.addInterceptorLast(new HttpResponseInterceptor() {
		// public void process(
		// final HttpResponse response,
		// final HttpContext context) throws HttpException, IOException {
		// HttpEntity entity = response.getEntity();
		//
		// }});
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpGet httpGet = new HttpGet(url);
		try {
			// 执行get请求
			HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			// 获取响应消息实体
			HttpEntity entity = httpResponse.getEntity();
			// Header ceheader = entity.getContentEncoding();
			// if (ceheader != null) {
			// HeaderElement[] codecs = ceheader.getElements();
			// for (int i = 0; i < codecs.length; i++) {
			// if (codecs[i].getName().equalsIgnoreCase("gzip")) {
			// httpResponse.setEntity(new
			// GzipDecompressingEntity(httpResponse.getEntity()));
			// }
			// }
			// }
			// 判断响应实体是否为空
			String content = null;
			if (entity != null) {
				content = EntityUtils.toString(entity);
			}
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpClient.close(); // 关闭流并释放资源
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}  
	 
    /** 
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
     */  
    public static String postHttp(String url,String data,String signature) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        try {  
        	httppost.setHeader("Content-type","application/xml;charset=GBK");
            httppost.setHeader("ACCESS_TOKEN",  GlobSerMan.map.get("AuthToken"));
            httppost.setHeader("SIGNATURE",signature);
            StringEntity s = new StringEntity(data);    
            httppost.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                	return EntityUtils.toString(entity, "UTF-8");
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {
			e.printStackTrace();
		} finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;
    }  
    
    /** 
     * HttpClient连接SSL 
     */  
    public void ssl() {  
        CloseableHttpClient httpclient = null;  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));  
            try {  
                // 加载keyStore d:\\tomcat.keystore    
                trustStore.load(instream, "123456".toCharArray());  
            } catch (java.security.cert.CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {  
                try {  
                    instream.close();  
                } catch (Exception ignore) {  
                }  
            }  
            // 相信自己的CA和所有自签名的证书  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();  
            // 只允许使用TLSv1协议  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,  
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);  
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();  
            // 创建http请求(get方式)  
            HttpGet httpget = new HttpGet("https://localhost:8443/myDemo/Ajax/serivceJ.action");  
            System.out.println("executing request" + httpget.getRequestLine());  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                HttpEntity entity = response.getEntity();  
                System.out.println("----------------------------------------");  
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    System.out.println("Response content length: " + entity.getContentLength());  
                    System.out.println(EntityUtils.toString(entity));  
                    EntityUtils.consume(entity);  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (KeyManagementException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (KeyStoreException e) {  
            e.printStackTrace();  
        } finally {  
            if (httpclient != null) {  
                try {  
                    httpclient.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    /** 
     * post方式提交表单（模拟用户登录请求） 
     */  
    public void postForm() {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("username", "admin"));  
        formparams.add(new BasicNameValuePair("password", "123456"));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    /** 
     * 发送 get请求 
     */  
    public void get() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet("http://www.baidu.com/");  
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 上传文件 
     */  
//    public void upload() {  
//        CloseableHttpClient httpclient = HttpClients.createDefault();  
//        try {  
//            HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");  
//  
//            FileEntity bin = new FileEntity(new File("F:\\image\\sendpix0.jpg"));  
//            StringEntity comment = new StringEntity("A binary file of some kind", ContentType.TEXT_PLAIN);  
//  
//            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();  
//  
//            httppost.setEntity(reqEntity);  
//  
//            System.out.println("executing request " + httppost.getRequestLine());  
//            CloseableHttpResponse response = httpclient.execute(httppost);  
//            try {  
//                System.out.println("----------------------------------------");  
//                System.out.println(response.getStatusLine());  
//                HttpEntity resEntity = response.getEntity();  
//                if (resEntity != null) {  
//                    System.out.println("Response content length: " + resEntity.getContentLength());  
//                }  
//                EntityUtils.consume(resEntity);  
//            } finally {  
//                response.close();  
//            }  
//        } catch (ClientProtocolException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        } finally {  
//            try {  
//                httpclient.close();  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
//        }  
//    }  
}