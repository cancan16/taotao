package com.taotao.search.mq.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

/**
 * @ClassName ItemMQHandler
 * @Author volc
 * @Description rabbitmq监听， 用于处理监听到的消息
 * @Date 2017年3月2日 下午2:34:37
 */
public class ItemMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private HttpSolrServer httpSolrServer;

    @Autowired
    private ItemService itemService;

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
	    String type = jsonNode.get("type").asText();
	    if (StringUtils.equals(type, "insert") || StringUtils.equals(type, "update")) {
		// 从后台系统中查询商品商品数据
		Item item = this.itemService.queryItemById(itemId);
		if (null != item) {
		    this.httpSolrServer.addBean(item);
		    this.httpSolrServer.commit();
		}
	    } else if (StringUtils.equals(type, "delete")) {
		// 删除索引数据
		this.httpSolrServer.deleteById(String.valueOf(itemId));
		this.httpSolrServer.commit();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
