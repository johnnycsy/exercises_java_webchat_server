package com.xinhai.nm.wechat.wechatserver.Manual;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    /*Get Request*/
    public static String get(String httpUrl) {
        String rs = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(httpUrl);
            CloseableHttpResponse respone = httpClient.execute(httpGet);
            HttpEntity entity = respone.getEntity();
            if (entity != null) {
                rs = EntityUtils.toString(entity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                /*关闭连接*/
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /*Post*/
    public static String post(String httpUrl, Map<String, String> map, String postType) {
        String rs = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(httpUrl);

            /*数据分解*/
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    nvp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            if (postType == "json") {
                ObjectMapper objectMapper = new ObjectMapper();
                String stringEntity = objectMapper.writeValueAsString(map);
                StringEntity entity = new StringEntity(stringEntity.toString(), "utf-8");
                httpPost.setEntity(entity);
                System.out.println(stringEntity);
            } else {
                httpPost.setEntity(new UrlEncodedFormEntity(nvp));
            }
//            System.out.println("=====>"+nvp);
            /*执行请求*/
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rs = EntityUtils.toString(entity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

}
