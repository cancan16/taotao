package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.User;
import com.taotao.web.threadLocal.UserThreadLocal;

/**
 * @ClassName OrderService
 * @Author volc
 * @Description 订单处理
 * @Date 2017年3月1日 下午12:26:07
 */
@Service
public class OrderService {

    @Autowired
    private ApiService apiService;

    @Value("${TAOTAO_ORDER_URL}")
    private String TAOTAO_ORDER_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @MethodName submitOrder
     * @Author volc
     * @Description 处理订单
     * @Date 2017年3月1日 下午12:29:59
     */
    public String submitOrder(Order order) {
	User user = UserThreadLocal.get();
	order.setUserId(user.getId());
	order.setBuyerNick(user.getUsername());
	try {
	    String url = TAOTAO_ORDER_URL + "/order/create";
	    HttpResult httpResult = this.apiService.doPostJson(url, MAPPER.writeValueAsString(order));
	    if (httpResult.getCode().intValue() == 200) { // 请求成功订，响应状态码，不一定提交成功，如果httpstatus为200(业务返回码)则下单成功
		String jsonData = httpResult.getData();
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		if (jsonNode.get("status").intValue() == 200) { // 下单成功
		    return jsonNode.get("data").asText(); // 返回订单号
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * @MethodName queryOrderById
     * @Author volc
     * @Description 根据订单ID查询定心详情
     * @Date 2017年3月1日 下午2:34:17
     */
    public Order queryOrderById(String orderId) {
	try {
	    String url = TAOTAO_ORDER_URL + "/order/query/" + orderId;
	    String jsonData = this.apiService.doGet(url);
	    if (StringUtils.isNotEmpty(jsonData)) {
	        return MAPPER.readValue(jsonData, Order.class);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

}
