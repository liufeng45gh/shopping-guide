package com.lucifer.utils;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtils {

	/**
	 * 发送 get请求
	 */
	public static String get(String url) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        String jsonStr = null;
        try {  
            // 创建httpget.    
           // HttpGet httpget = new HttpGet("http://115.29.109.91:8080/data/follow-clubs?ids=" + idsStr);  
           // HttpGet httpget = new HttpGet("http://192.168.0.101:8888/follow-clubs?ids=" + idsStr); 
            HttpGet httpget = new HttpGet(url); 
            // System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();
                // System.out.println("--------------------------------------");  
                // 打印响应状态 
               System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    //System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    // System.out.println("Response content: " + EntityUtils.toString(entity));
                	jsonStr = EntityUtils.toString(entity);
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
        return jsonStr;
    }

    public static String post(String url,String data) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String jsonStr = null;
        try {
            // 创建httpget.
            // HttpGet httpget = new HttpGet("http://115.29.109.91:8080/data/follow-clubs?ids=" + idsStr);
            // HttpGet httpget = new HttpGet("http://192.168.0.101:8888/follow-clubs?ids=" + idsStr);
            HttpPost httpPost = new HttpPost(url);
            HttpEntity send_entity = new StringEntity(data);
            httpPost.setEntity(send_entity);
            httpPost.setHeader("Content-Type", "application/json");
            // System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    //System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    // System.out.println("Response content: " + EntityUtils.toString(entity));
                    jsonStr = EntityUtils.toString(entity);
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
        return jsonStr;
    }


}
