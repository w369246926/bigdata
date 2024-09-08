package com.flink.sockettest.http;


public class HttpClientResult {

    private int statusCode; // 请求网页返回状态代码如果是200，则请求页面成功。


    private String content; // 返回数据内容

    public HttpClientResult(int statusCode ,String content){
        this.statusCode=statusCode;
        this.content=content;
    }
    public HttpClientResult(int statusCode){
        this.statusCode=statusCode;
    }

    /**
     * 网站的访问状态
     *
     * @return 200为访问页面成功。详细请查阅http协议的材料
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 返回页面内容
     *
     *            页面编码
     * @return 字符串。如果异常返回null
     */
    public String getContent() {
        return content;
    }
}
