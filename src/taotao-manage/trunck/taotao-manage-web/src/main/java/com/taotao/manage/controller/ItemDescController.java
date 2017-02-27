package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

/**
 * @ClassName ItemDescController
 * @Description 根据商品ID查询商品描述数据
 * @Author youtanzhi
 * @Date 2017年1月27日 下午5:15:46
 */
@RequestMapping("item/desc")
@Controller
public class ItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryByItemId(@PathVariable("itemId") Long itemId) {

	ItemDesc itemDesc = itemDescService.queryById(itemId);
	try {
	    if (null == itemDesc) {
		// 资源不存在 404
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    // 请求返回正常200
	    return ResponseEntity.ok(itemDesc);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// 出错500
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
