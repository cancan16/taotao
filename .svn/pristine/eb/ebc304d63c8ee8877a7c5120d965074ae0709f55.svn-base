package com.taotao.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;

/**
 * @ClassName ItemController
 * @Description 商品操作控制器
 * @Author youtanzhi
 * @Date 2016年12月30日 下午8:08:01
 */
@RequestMapping("item")
@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    ItemService itemService;

    /**
     * @Title saveItem @Description 添加商品 @return ResponseEntity<Void> @throws
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams) {
	try {
	    if (LOGGER.isInfoEnabled()) { // 判断是否启用了info级别日志再进行日志输出
		// logger日志占位符{}对应后面的参数
		LOGGER.info("新增商品, item = {}, desc = {}", item, desc);
	    }
	    itemService.saveItem(item, desc,itemParams);
	    // 成功201
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("新增商品成功, itemId = {}", item.getId());
	    }
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	} catch (Exception e) {
	    LOGGER.error("新增商品失败! titel = " + item.getTitle() + ", cid = " + item.getCid(), e);
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Title queryItemList
     * @Description TODO
     * @param page
     * @param rows
     * @return ResponseEntity<EasyUIResult>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "30") Integer rows) {
	try {
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("参数   page = {}, rows = {}", page, rows);
	    }
	    PageInfo<Item> pageInfo = itemService.queryPageList(page, rows);
	    EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	    return ResponseEntity.ok(easyUIResult);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// 出错500
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Title updateItem
     * @Description 修改商品信息
     * @param item
     * @param desc
     * @return ResponseEntity<Void>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc, @RequestParam("itemParams") String itemParams) {
	try {
	    if (LOGGER.isInfoEnabled()) { // 判断是否启用了info级别日志再进行日志输出
		// logger日志占位符{}对应后面的参数
		LOGGER.info("修改商品, item = {}, desc = {}", item, desc);
	    }
	    // 修改商品的基本数据
	    this.itemService.updateItem(item, desc,itemParams);
	    // 成功201
	    if (LOGGER.isInfoEnabled()) {
		LOGGER.info("修改商品成功, itemId = {}", item.getId());
	    }
	    // 成功返回204
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception e) {
	    LOGGER.error("修改商品失败! titel = " + item.getTitle() + ", cid = " + item.getCid(), e);
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
