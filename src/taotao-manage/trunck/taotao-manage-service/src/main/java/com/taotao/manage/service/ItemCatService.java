package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {
    // @Autowired
    // private ItemCatMapper catMapper;
    /**
     * @Title queryItemCatListByParentId
     * @Description 查询商品目录
     * @param id
     * @return List<ItemCat>
     * @throws 
     */
    // public List<ItemCat> queryItemCatListByParentId(Long id) {
    // ItemCat itemCat = new ItemCat();
    // itemCat.setParentId(id);
    // return catMapper.select(itemCat);
    // }
    // @Override
    // public Mapper<ItemCat> getMapper() {
    // return catMapper;
    // }

    @Autowired
    private RedisService redisService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    private static final String REDIS_KEY = "TAOTAO_MANAGE_ITEM_CAT_ALL"; // 最佳实践，项目名+模块名+业务名

    private static final Integer REDIS_TIME = 60 * 60 * 24 * 30 * 3;

    /**
     * 全部查询，并且生成树状结构
     * @return
     */
    public ItemCatResult queryAllToTree() {
	ItemCatResult result = new ItemCatResult();
	/*
	 * 先从缓存中命中，如果命中就返回，如果没有命中执行以下程序 ，这里加入缓存有可能影响业务逻辑的正常执行，这里需要进行优化处理
	 * 进行trycatch，出现异常捕获后继续向下进行
	 */
	try {
	    String cacheData = this.redisService.get(REDIS_KEY);
	    /**
	     * cacheData格式
	        {
	          "data": [
	            {
	              "u": "/products/1.html",
	              "n": "<a href='/products/1.html'>图书、音像、电子书刊</a>",
	              "i": [
	                {
	                  "u": "/products/2.html",
	                  "n": "电子书刊",
	                  "i": [
	                    "/products/3.html|电子书",
	                    "/products/4.html|网络原创",
	                    "/products/5.html|数字杂志",
	                    "/products/6.html|多媒体图书"
	                  ]
	                },
	                {
	                  "u": "/products/7.html",
	                  "n": "音像",
	                  "i": [
	                    "/products/8.html|音乐",
	                    "/products/9.html|影视",
	                    "/products/10.html|教育音像"
	                  ]
	                }
	              ]
	            }
	          ]
	        }
	     */
	    if (StringUtils.isNotEmpty(cacheData)) {
		// 命中,进行反序列化，ItemCatData中items是个泛型的list，并不会转成具体的对象
		return MAPPER.readValue(cacheData, ItemCatResult.class);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	// 全部查出，并且在内存中生成树形结构
	List<ItemCat> cats = super.queryAll();
	// 转为map存储，key为父节点ID，value为数据集合
	Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
	for (ItemCat itemCat : cats) {
	    if (!itemCatMap.containsKey(itemCat.getParentId())) {
		itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
	    }
	    itemCatMap.get(itemCat.getParentId()).add(itemCat);
	}

	// 封装一级对象
	List<ItemCat> itemCatList1 = itemCatMap.get(0L);
	for (ItemCat itemCat : itemCatList1) {
	    ItemCatData itemCatData = new ItemCatData();
	    itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
	    itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
	    result.getItemCats().add(itemCatData);
	    if (!itemCat.getIsParent()) {
		continue;
	    }

	    // 封装二级对象
	    List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
	    List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
	    itemCatData.setItems(itemCatData2);
	    for (ItemCat itemCat2 : itemCatList2) {
		ItemCatData id2 = new ItemCatData();
		id2.setName(itemCat2.getName());
		id2.setUrl("/products/" + itemCat2.getId() + ".html");
		itemCatData2.add(id2);
		if (itemCat2.getIsParent()) {
		    // 封装三级对象
		    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
		    List<String> itemCatData3 = new ArrayList<String>();
		    id2.setItems(itemCatData3);
		    for (ItemCat itemCat3 : itemCatList3) {
			itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
		    }
		}
	    }
	    if (result.getItemCats().size() >= 14) {
		break;
	    }
	}

	/*
	 * 这里不能用JsonProcessingException进行捕获，需要使用Exception大的异常进行获取添加缓存时的异常
	 */
	try {
	    // 将结果集写入到缓存中
	    this.redisService.set(REDIS_KEY, MAPPER.writeValueAsString(result), REDIS_TIME);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

}
