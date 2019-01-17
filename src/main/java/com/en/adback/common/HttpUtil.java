package com.en.adback.common;


import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */
public class HttpUtil {

    private HttpGet httpGet;
    private HttpPost httpPost;
    public  String TIME_OUT = "请求超时";
    public  String SERVER_ERROR = "服务器异常";
    public  String NETWORK_ERROR = "网络异常";
    /**
     * Http Get请求 请求地址
     *
     * @param url    Get参数
     * @param params 编码
     * @param encode 返回请求结果
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public  String sendGetRequest(String url, Map<String, String> params,
                                        String encode) throws UnsupportedEncodingException {
            CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
            try{


                            if (null == params) {
                                httpGet = new HttpGet(url);
                            } else {
                                httpGet = new HttpGet(url + dealGetParams(params, encode));
                            }
                            CloseableHttpResponse response  = null;
                            try {
                                response = httpClient.execute(httpGet);
                                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                                    try {
                                        result = EntityUtils.toString(response.getEntity());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }finally {
                                try {
                                    response.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
            }finally {
                try {
                       httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        return result != null ? result : "";
    }

    public  String sendGetRequest(String url, Map<String, String> params)
            throws ClientProtocolException, IOException {
        return sendGetRequest(url, params, "UTF-8");
    }

    public  String sendGetRequest(String url)
            throws ClientProtocolException, IOException {
        return sendGetRequest(url, null, "UTF-8");
    }

    public  String sendGetRequestGB2312(String url)
            throws ClientProtocolException, IOException {
        return sendGetRequest(url, null, "GB2312");
    }

    /**
     * POST请求 返回请求结果 HashMap键值参数
     *
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public  String sendPostRequest(String url, Object params, String encode) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultStr = null;
        try {
            httpPost = new HttpPost(url);
            if (params != null) {
                StringEntity entity;
                if (params instanceof Map) {
                    entity = new StringEntity(dealPostParams(
                            (HashMap<String, String>) params, encode));
                } else if (params instanceof String) {
                    entity = new StringEntity((String) params, encode);
                } else if (params instanceof List) {
                    entity = new UrlEncodedFormEntity(
                            (List<? extends NameValuePair>) params, encode);
                } else {
                    throw new Exception("参数有误!");
                }
                          httpPost.setHeader("Authorization","Bearer access_token");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    resultStr = EntityUtils.toString(response.getEntity());
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        }finally {
            httpClient.close();
        }
        return resultStr;
    }

    public  String sendPostRequest(String url) throws Exception {
        return sendPostRequest(url,"");
    }

    /**
     * 键值对请求 默认UTF-8编码
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public  String sendPostRequest(String url, Map<String, String> params)
            throws Exception {
        return sendPostRequest(url, params, "UTF-8");
    }

    /**
     * String 默认UTF-8编码
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public  String sendPostRequest(String url, String params)
            throws Exception {
        return sendPostRequest(url, params, "UTF-8");
    }

    /**
     * 键值对请求 默认UTF-8编码
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public  String sendPostRequest(String url, List<NameValuePair> params)
            throws Exception {
        return sendPostRequest(url, params, "UTF-8");
    }

    /**
     * 处理Get方式请求的URL
     *
     * @param params
     * @param enc
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String dealGetParams(Map<String, String> params, String enc)
            throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // 参数名=参数&参数名=参数

            sb.append(entry.getKey().toString()).append("=")
                    .append(URLEncoder.encode(String.valueOf(entry.getValue()), enc))
                    .append("&");
        }
        // 删除最后一个&
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 处理POST请求URL
     *
     * @param params
     * @param enc
     * @return
     */
    private static String dealPostParams(Map<String, String> params, String enc) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                sb.append(entry.getKey()).append("=")
                        .append(URLEncoder.encode(entry.getValue().toString(), enc))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }



    public   static String connURL(Map<String,String> paras, String url){


        String jsonStr=dealPostParams(paras,"utf-8");
        StringBuffer sb = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            // 连接超时
            conn.setConnectTimeout(25000);
            // 读取超时 --服务器响应比较慢,增大时间
            conn.setReadTimeout(25000);

            HttpURLConnection.setFollowRedirects(true);

            conn.setRequestMethod("POST");

            conn.setDoOutput(true);

            conn.setDoInput(true);

            conn.connect();

            OutputStream os = conn.getOutputStream();

            if(jsonStr != null && !jsonStr.equals(""))os.write(jsonStr.getBytes("UTF-8"));

            os.flush();

            os.close();

            InputStream is = conn.getInputStream();

            int size = is.available();

            byte[] jsonBytes = new byte[size];

            is.read(jsonBytes);

            String msg = new String(jsonBytes,"UTF-8");
            return msg;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "执行失败！";
    }





}
