package com.taotao.search.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.search.pojo.Item;

/**
 * @ClassName ItemService
 * @Author volc
 * @Description 搜索系统对商品数据处理
 * @Date 2017年3月2日 下午3:39:43
 */
@Service
public class ItemService {

    @Autowired // 使用ApiService就必须有httpclient环境
    private ApiService apiService;

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @MethodName queryItemById
     * @Author volc
     * @Description 根据商品ID查询商品
     * @Date 2017年3月2日 下午3:48:31
     */
    public Item queryItemById(Long itemId) {
	try {
	    String url = TAOTAO_MANAGE_URL + "/rest/item/" + itemId;
	    String jsonData = this.apiService.doGet(url);
	    if (StringUtils.isEmpty(jsonData)) {
		return null;
	    }
	    return MAPPER.readValue(jsonData, Item.class);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
