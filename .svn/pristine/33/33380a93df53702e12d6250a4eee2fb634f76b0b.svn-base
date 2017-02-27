package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

/**
 * @ClassName ItemParamItemController
 * @Description 规格参数数据
 * @Author youtanzhi
 * @Date 2017年2月11日 上午7:47:07
 */
@RequestMapping("item/param/item")
@Controller
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * @Title queryByItemId
     * @Description 根据商品ID查询商品规格参数信息
     * @return ResponseEntity<ItemParamItem>
     * @throws 
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryByItemId(@PathVariable("itemId") Long itemId) {
	try {
	    ItemParamItem record = new ItemParamItem();
	    record.setItemId(itemId);
	    ItemParamItem item = this.itemParamItemService.queryOne(record);
	    if (null == item) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(item);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// 500
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
