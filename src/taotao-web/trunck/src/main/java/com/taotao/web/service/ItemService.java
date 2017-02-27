package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.web.bean.Item;

/**
 * @ClassName ItemService
 * @Author volc
 * @Description 商品详情操作
 * @Date 2017年2月27日 下午11:02:11
 */
@Service
public class ItemService {

    @Autowired
    private ApiService apiService;
    private static final ObjectMapper MAPPRE = new ObjectMapper();

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    /**
     * @MethodName queryItemById
     * @Author volc
     * @Description 根据商品ID查询商品的详情
     * @Date 2017年2月27日 下午11:03:45
     */
    public Item queryItemById(Long itemId) {
	try {
	    String url = TAOTAO_MANAGE_URL + "/rest/item/" + itemId;
	    String jsonData = this.apiService.doGet(url);
	    if (StringUtils.isEmpty(jsonData)) {
		return null;
	    }
	    return MAPPRE.readValue(jsonData, Item.class);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
