package com.taotao.web.mq.handler;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

/**
 * @ClassName ItemMQHandler
 * @Author volc
 * @Description rabbitmq监听， 用于处理监听到的消息
 * @Date 2017年3月2日 下午2:34:37
 */
public class ItemMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisService redisService;

    /**
     * @MethodName excute
     * @Author volc
     * @Description 处理后台发送的消息，这里捕获到rabbitmq进行处理
     * @Date 2017年3月2日 下午2:50:31
     */
    public void excute(String message) {
	try {
	    JsonNode jsonNode = MAPPER.readTree(message);
	    Long itemId = jsonNode.get("itemId").asLong();
	    String key = ItemService.REDIS_KEY + itemId;
	    this.redisService.del(key);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
