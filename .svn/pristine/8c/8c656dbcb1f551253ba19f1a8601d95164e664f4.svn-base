package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

/**
 * @ClassName ContentController
 * @Description 首页内容管理
 * @Author youtanzhi
 * @Date 2017年2月19日 下午6:50:39
 */
@RequestMapping("content")
@Controller
public class ContentController {
    @Autowired
    private ContentService contenetService;

    /**
     * @Title saveContent
     * @Description 新增内容
     * @return ResponseEntity<Void>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content) {
	try {
	    content.setId(null);
	    this.contenetService.save(content);
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Title queryList
     * @Description 查询对应的节点广告内容列表
     * @param content
     * @return ResponseEntity<Void>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryList(@RequestParam("categoryId") Long categoryId, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "10") Integer rows) {
	try {
	    EasyUIResult result = this.contenetService.queryList(categoryId,page, rows);
	    return ResponseEntity.ok(result);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
