package com.flink.sockettest.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
     * @Description: httpclient常用方法封装,
     * @Author: ggf
     * @Date: 2020/06/06
     */
    public class HttpClientUtils {
    /**
     * 编码方式
     */
    private static final String ENCODING = "UTF-8";
    /**
     * 连接超时时间，60秒
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 6000;
    /**
     * socket连接超时时间，60秒
     */
    public static final int DEFAULT_READ_TIMEOUT = 6000;
    /**
     * 请求超时时间，60秒
     */
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 6000;
    /**
     * 最大连接数,默认为2
     */
    private static final int MAX_TOTAL = 64;
    /**
     * 设置指向特定路由的并发连接总数，默认为2
     */
    private static final int MAX_PER_ROUTE = 32;

    private static RequestConfig requestConfig;
    private static PoolingHttpClientConnectionManager connectionManager;
    private static BasicCookieStore cookieStore;
    private static HttpClientBuilder httpBuilder;
    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient httpsClient;
    private static SSLContext sslContext;


    /**
     * 创建SSLContext对象，用来绕过https证书认证实现访问。
     */
    static {
        try {
            sslContext = SSLContext.getInstance("TLS");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpClientContext addUserOAuth(String username, String password) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        org.apache.http.auth.Credentials credentials = new org.apache.http.auth.UsernamePasswordCredentials(username, password);
        credsProvider.setCredentials(org.apache.http.auth.AuthScope.ANY, credentials);
        HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setCredentialsProvider(credsProvider);
        return httpClientContext;
    }

    /**
     * 初始化httpclient对象，以及在创建httpclient对象之前的一些自定义配置。
     */
    static {
        // 自定义配置信息
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(DEFAULT_READ_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
                .build();
        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置cookie存储对像，在需要获取cookie信息时，可以使用这个对象。
        cookieStore = new BasicCookieStore();
        // 设置最大连接数
        connectionManager.setMaxTotal(MAX_TOTAL);
        // 设置路由并发数
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpBuilder.setDefaultCookieStore(cookieStore);
        // 实例化http 和 https的对象。
        httpClient = httpBuilder.build();
        httpsClient = (CloseableHttpClient) wrapClient();
    }

    /**
     * 封装无参数的get请求（http）
     *
     * @param url 请求url
     * @return 返回对象HttpClientResult
     */
    public static HttpClientResult doGet(String url, HttpClientContext httpClientContext) {
        return doGet(url, false, httpClientContext);
    }

    /**
     * 封装无参get请求，支持https协议
     *
     * @param url   请求url
     * @param https 请求的是否是https协议，是：true  否false
     * @return
     */
    public static HttpClientResult doGet(String url, boolean https, HttpClientContext httpClientContext) {
        return doGet(url, null, null, https, httpClientContext);
    }

    /**
     * 封装带参数的get请求，支持https协议
     *
     * @param url    请求url
     * @param params 请求参数
     * @param https  是否是https协议
     */
    public static HttpClientResult doGet(String url, Map<String, String> params, boolean https, HttpClientContext httpClientContext) {
        return doGet(url, null, params, https, httpClientContext);
    }

    /**
     * 封装带参数和带请求头信息的GET方法，支持https协议请求
     *
     * @param url     请求url
     * @param headers 请求头信息
     * @param params  请求参数
     * @param https   是否使用https协议
     */
    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params, boolean https, HttpClientContext httpClientContext) {
        // 创建HttpGet
        HttpGet httpGet = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            if (params != null) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            // 创建HTTP对象
            httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(requestConfig);
            // 设置请求头
            setHeader(headers, httpGet);
            // 使用不同的协议进行请求，返回自定义的响应对象
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpGet, httpClientContext);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpGet, httpClientContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            release(httpResponse);
        }

        return null;
    }

    /**
     * POST不带参数，只支持http协议
     *
     * @param url 请求url
     */
    public static HttpClientResult doPost(String url, HttpClientContext httpClientContext) {
        return doPost(url, Boolean.FALSE, httpClientContext);
    }

    /**
     * 封装不带参数的post请求，支持https协议
     *
     * @param url   请求url
     * @param https 是否是https协议
     */
    public static HttpClientResult doPost(String url, boolean https, HttpClientContext httpClientContext) {
        return doPost(url, null, (Map<String, Object>) null, https, httpClientContext);
    }

    /**
     * 带参数的post请求，支持https协议
     *
     * @param url    请求url
     * @param params 请求参数
     * @param https  是否是https协议
     */
    public static HttpClientResult doPost(String url, Map<String, Object> params, boolean https, HttpClientContext httpClientContext) {
        return doPost(url, null, params, https, httpClientContext);
    }

    /**
     * 自定义httpPost对象的的post请求，支持https协议
     *
     * @param params 请求参数
     * @param https  是否是https协议
     */
    public static HttpClientResult doPost(Map<String, Object> params, boolean https, HttpClientContext httpClientContext, HttpPost httpPost) {
        return doPost(null, params, https, httpClientContext, httpPost);
    }

    /**
     * 带参数和请求头的POST请求，支持https
     *
     * @param url     请求url
     * @param headers 请求头
     * @param params  请求参数，参数为K=V格式
     * @param https   是否https协议
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, Object> params, boolean https, HttpClientContext httpClientContext) {
        // 创建HTTP对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头
        setHeader(headers, httpPost);
        // 封装请求参数
        setParam(params, httpPost);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost, httpClientContext);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost, httpClientContext);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 带参数、带请求头的POST请求，支持https协议
     *
     * @param url     请求url
     * @param headers 请求头
     * @param json    请求参数为json格式
     * @param https   是否使用https协议
     * @throws Exception
     */
    public static HttpClientResult doPost(String url, Map<String, String> headers, String json, boolean https, HttpClientContext httpClientContext) {
        // 创建HTTP对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头
        setHeader(headers, httpPost);
        StringEntity stringEntity = new StringEntity(json, ENCODING);
        stringEntity.setContentEncoding(ENCODING);
        httpPost.setEntity(stringEntity);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost, httpClientContext);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost, httpClientContext);
            }
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 自定义httpPost对象的POST方法，支持https协议
     *
     * @param headers  请求头
     * @param https    是否使用https协议
     * @param httpPost 自定义httpPost对象
     * @throws Exception
     */
    public static HttpClientResult doPost(Map<String, String> headers, Map<String, Object> params, boolean https, HttpClientContext httpClientContext, HttpPost httpPost) {
        // 创建HTTP对象
        // 设置请求头
        setHeader(headers, httpPost);
        // 封装请求参数
        setParam(params, httpPost);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost, httpClientContext);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost, httpClientContext);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }


    /**
     * 带参数和请求头的POST请求，支持https
     *
     * @param url     请求url
     * @param headers 请求头
     * @param params  请求参数，参数为K=V格式
     * @param https   是否https协议
     */
    public static HttpClientResult doPostFrom(String url, Map<String, String> headers, Map<String, Object> params, boolean https, HttpClientContext httpClientContext) {
        // 创建HTTP对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置请求头
        setHeader(headers, httpPost);
        // 封装请求参数
        setParamForm(params, httpPost);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            if (https) {
                return getHttpClientResult(httpResponse, httpsClient, httpPost, httpClientContext);
            } else {
                return getHttpClientResult(httpResponse, httpClient, httpPost, httpClientContext);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
            httpPost.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url) {
        return doPut(url, null, null);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url    请求地址
     * @param params 参数集合
     * @return
     * @throws Exception
     */
    public static HttpClientResult doPut(String url, Map<String, Object> params, HttpClientContext httpClientContext) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setConfig(requestConfig);
        setParam(params, httpPut);
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPut, httpClientContext);
        } finally {
            httpPut.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 发送delete请求，不带请求参数
     *
     * @param url 请求url
     * @return
     * @throws Exception
     */
    public static HttpClientResult doDelete(String url, HttpClientContext httpClientContext) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete, httpClientContext);
        } finally {
            httpDelete.releaseConnection();
            release(httpResponse);
        }
    }

    /**
     * 发送delete请求，带请求参数， 支持https协议
     *
     * @param url    请求url
     * @param params 请求参数
     * @param https  是否https
     * @return
     * @throws Exception
     */
    public static HttpClientResult doDelete(String url, Map<String, Object> params, boolean https, HttpClientContext httpClientContext) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("_method", "delete");
        return doPost(url, params, https, httpClientContext);
    }

    /**
     * 获取cookie信息
     *
     * @return 返回所有cookie集合
     */
    public static List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }


    /**
     * 设置封装请求头
     *
     * @param params     头信息
     * @param httpMethod 请求对象
     */
    public static void setHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (null != params && !params.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 封装请求参数
     *
     * @param params     请求参数
     * @param httpMethod 请求方法
     */
    public static void setParam(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod) {
        // 封装请求参数
        if (null != params && !params.isEmpty()) {
            StringEntity entity = new StringEntity(JSONObject.toJSONString(params), ContentType.APPLICATION_JSON);
            // 设置到请求的http对象中
            httpMethod.setEntity(entity);
        }
    }


    /**
     * 封装请求参数
     * <p>
     * ContentType.APPLICATION_FORM_URLENCODED
     *
     * @param params     请求参数
     * @param httpMethod 请求方法
     */
    public static void setParamForm(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod) {
        // 封装请求参数
        if (null != params && !params.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(nvps, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//                StringEntity entity = new StringEntity(JSONObject.toJSONString(params),ContentType.APPLICATION_JSON );
            // 设置到请求的http对象中
            httpMethod.setEntity(entity);
        }
    }

    /**
     * 封装请求参数
     * <p>
     * ContentType.APPLICATION_FORM_URLENCODED
     *
     * @param params     请求参数
     * @param httpMethod 请求方法
     */
    public static void setDeleteParamForm(Map<String, Object> params, HttpEntityEnclosingRequestBase httpMethod) {
        // 封装请求参数
        if (null != params && !params.isEmpty()) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(nvps, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//                StringEntity entity = new StringEntity(JSONObject.toJSONString(params),ContentType.APPLICATION_JSON );
            // 设置到请求的http对象中
            httpMethod.setEntity(entity);
        }
    }

    /**
     * 获得响应结果
     *
     * @param httpResponse 响应对象
     * @param httpClient   httpclient对象
     * @param httpMethod   请求方法
     * @return
     * @throws Exception
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod, HttpClientContext httpClientContext) {
        try {
            // 执行请求
            if (httpClientContext == null) {
                httpResponse = httpClient.execute(httpMethod);
            } else {
                httpResponse = httpClient.execute(httpMethod, httpClientContext);
            }
            // 获取返回结果
            if (httpResponse != null && httpResponse.getStatusLine() != null) {
                String content = "";
                if (httpResponse.getEntity() != null) {
                    content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
                }
                return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
            }
        } catch (IOException e) {
//                e.printStackTrace();
            System.out.println("请求接口失败" + e.getMessage());
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

        /**
         * 释放资源
         *
         * @param httpResponse 响应对象
         */
        public static void release(CloseableHttpResponse httpResponse) {
            // 释放资源
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static HttpClient wrapClient() {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] arg0,
                                                   String arg1) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] arg0,
                                                   String arg1) throws CertificateException {
                    }
                };
                ctx.init(null, new TrustManager[] { tm }, null);
                SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
                        ctx, NoopHostnameVerifier.INSTANCE);
                CloseableHttpClient httpclient = HttpClients.custom()
                        .setSSLSocketFactory(ssf).build();
                return httpclient;
            } catch (Exception e) {
                return (HttpClient) HttpClients.createDefault();
            }
        }

    }
