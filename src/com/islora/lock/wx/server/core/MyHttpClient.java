package com.islora.lock.wx.server.core;

import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {
	public static void main(String[] args)throws Exception{
		httpGet();
	}
	
	public static String getCryptoByLockSn(String sn){
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            
            HttpGet httpget = new HttpGet(MyProp.getPropStr("webmain.ip")+"/api/crypto?cmd=cryptobyid&id="+sn);  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    return EntityUtils.toString(entity);
                }  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
        return null;
	}
	public static String getCryptoString(String byteString){
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            
            HttpGet httpget = new HttpGet(MyProp.getPropStr("webmain.ip")+"/api/crypto?cmd=crypto&value="+byteString);  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    return EntityUtils.toString(entity);
                }  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
        return null;
	}
	
	public static String getWebmainRequest(String requestName,Map<String,String> map){
		CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            Set<String> set=map.keySet();
            StringBuilder sb=new StringBuilder();
            sb.append(MyProp.getPropStr("webmain.ip")).append("/api/").append(requestName).append("?");
            for(String s:set) {
            	sb.append(s).append("=").append(map.get(s)).append("&");
            }
            sb.deleteCharAt(sb.length()-1);
            HttpGet httpget = new HttpGet(sb.toString());  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    return EntityUtils.toString(entity);
                }  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                httpclient.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
        return null;
	}
	
	
	public static void httpGet() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet("http://127.0.0.1:8888/api/crypto?cmd=crypto&value=01,02,03,04,05,06,07");  
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }

}
