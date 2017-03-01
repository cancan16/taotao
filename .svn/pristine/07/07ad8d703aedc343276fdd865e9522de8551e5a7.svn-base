package com.taotao.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.taotao.common.httpclient.HttpResult;

/**
 * @ClassName ApiService
 * @Author volc
 * @Description 封装通用的请求方式
 * @Date 2017年2月25日 上午12:53:33
 */
@Service
public class ApiService implements BeanFactoryAware {
    // 注入httpclient对象
    /**
     * httpclient应该是多例的，但是在这里已经注入了，调用service时就是单例，应该修改为多例模式
     */
    // @Autowired
    // private CloseableHttpClient httpclient;

    /**
     * @Fields beanFactory
     * bean工厂可以解决httpclient多例的问题
     */
    private BeanFactory beanFactory;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * @MethodName doGet 
     * @Author volc
     * @Description 不带参数的get请求， 执行GET请求，响应200返回内容，如果响应404返回null
     * @Date 2017年2月25日 上午12:54:48
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
	// 创建http GET请求
	HttpGet httpGet = new HttpGet(url);
	httpGet.setConfig(requestConfig);
	CloseableHttpResponse response = null;
	try {
	    // 执行请求
	    response = getHttpClient().execute(httpGet);
	    // 判断返回状态是否为200
	    if (response.getStatusLine().getStatusCode() == 200) {
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	    }
	} finally {
	    if (response != null) {
		response.close();
	    }
	}
	return null;
    }

    /**
     * @MethodName doGet 
     * @Author volc
     * @Description 带参数的GET请求
     * @Date 2017年2月25日 上午12:55:13
     */
    public String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
	// 创建构建uri对象
	URIBuilder uriBuilder = new URIBuilder(url);
	for (Map.Entry<String, String> entry : params.entrySet()) {
	    uriBuilder.setParameter(entry.getKey(), entry.getValue());
	}
	return doGet(uriBuilder.build().toString());
    }

    /**
     * @MethodName doPost 
     * @Author volc
     * @Description 执行post请求 封装通用的post请求(带参和不带参)
     * @Date 2017年2月25日 上午10:40:09
     */
    public HttpResult doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
	// 创建http POST请求
	HttpPost httpPost = new HttpPost(url);
	httpPost.setConfig(requestConfig);
	if (null != params) {
	    // 设置1个post参数
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
	    for (Map.Entry<String, String> entry : params.entrySet()) {
		parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	    }
	    // 构造一个form表单式的实体
	    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
	    // 将请求实体设置到httpPost对象中
	    httpPost.setEntity(formEntity);
	}
	CloseableHttpResponse response = null;
	try {
	    // 执行请求
	    response = getHttpClient().execute(httpPost);
	    // 无论什么情况都返回
	    return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
	} finally {
	    if (response != null) {
		response.close();
	    }
	}
    }

    /**
     * @MethodName getHttpClient 
     * @Author volc
     * @Description 封装获取多例的httpclient，因为spring注入的时httpclient为多例，每次获取都是多例的
     * @Date 2017年2月25日 上午10:52:21
     */
    private CloseableHttpClient getHttpClient() {
	return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    /**
     * @Title setBeanFactory
     * @Description 设置spring注入的bean为多例,spring初始化时会调用这个覆盖的方法,(设置beanFactory对象)
     * @param beanFactory
     * @throws BeansException
     * @throws 
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	this.beanFactory = beanFactory;
    }
    
    /**
     * @MethodName doPostJson
     * @Author volc
     * @Description 请求类型：APPLICATION_JSON，方式：post
     * @Date 2017年3月1日 下午12:38:31
     */
    public HttpResult doPostJson(String url, String json) throws ClientProtocolException, IOException, URISyntaxException {
	// 创建http POST请求
	HttpPost httpPost = new HttpPost(url);
	httpPost.setConfig(requestConfig);
	if (null != json) {
	    // 构造一个form表单式的实体
	    StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
	    // 将请求实体设置到httpPost对象中
	    httpPost.setEntity(stringEntity);
	}
	CloseableHttpResponse response = null;
	try {
	    // 执行请求
	    response = getHttpClient().execute(httpPost);
	    // 无论什么情况都返回
	    return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
	} finally {
	    if (response != null) {
		response.close();
	    }
	}
    }
}
