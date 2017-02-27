package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;

/**
 * @ClassName ItemParamController
 * @Description 商品规格模板操作
 * @Author youtanzhi
 * @Date 2017年2月7日 下午10:46:55
 */
@RequestMapping("item/param")
@Controller
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    /**
     * @Title queryByItemCatId
     * @Description 查询类目ID查找商品规格模板
     * @param itemCatId
     * @return ResponseEntity<ItemParam>
     * @throws 
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") Long itemCatId) {
	try {
	    ItemParam record = new ItemParam();
	    record.setItemCatId(itemCatId);
	    ItemParam itemParam = this.itemParamService.queryOne(record);
	    if (null == itemParam) { // 不存在为404
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(itemParam);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * @Title saveItemParam
     * @Description 新增规格参数模板
     * @param paramData
     * @param itemCatId
     * @return ResponseEntity<Void>
     * @throws 
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@RequestParam("paramData") String paramData, @PathVariable("itemCatId") Long itemCatId) {
	// @RequestParam和@PathVariable占位符获取提交的数据
	System.out.println("test");
	try {
	    ItemParam itemParam = new ItemParam();
	    itemParam.setItemCatId(itemCatId);
	    itemParam.setParamData(paramData);
	    this.itemParamService.save(itemParam);
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
