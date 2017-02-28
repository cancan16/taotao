package com.taotao.common.httpclient;

/**
 * @ClassName HttpResult
 * @Author volc
 * @Description http请求结果
 * @Date 2017年2月25日 上午10:29:07
 */
public class HttpResult {

    private Integer code; // 状态码
    private String data; // 返回数据

    @Override
    public String toString() {
	return "HttpResult [code=" + code + ", data=" + data + "]";
    }

    public HttpResult(Integer code, String data) {
	this.code = code;
	this.data = data;
    }

    public HttpResult() {
    }

    public Integer getCode() {
	return code;
    }

    public void setCode(Integer code) {
	this.code = code;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }
}
