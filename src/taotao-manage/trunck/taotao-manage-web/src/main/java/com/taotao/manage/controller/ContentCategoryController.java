package com.taotao.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.ContentCategoryService;

/**
 * @ClassName ContentCategoryController
 * @Description 网站内容管理类
 * @Author youtanzhi
 * @Date 2017年2月18日 下午7:10:27
 */
@RequestMapping("content/category")
@Controller
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * @Title queryContentCategory
     * @Description 根据父节点ID查询子节点网站内容数据tree返回list集合
     * @return ResponseEntity<List<ContentController>>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
	try {
	    ContentCategory record = new ContentCategory();
	    record.setParentId(parentId);
	    List<ContentCategory> contentCategoryList = this.contentCategoryService.queryListByWhere(record); // 根据条件查询
	    if (null == contentCategoryList || contentCategoryList.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(contentCategoryList);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Title saveContentCategory
     * @Description 新增子节点返回该节点对象 TODO 修改事物的问题不该修改放到controller中来处理，这一系列操作放应该放到service处理保证事物同步
     * @param parentId
     * @return ResponseEntity<ContentCategory>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory) {
	try {
	    contentCategory.setId(null);
	    contentCategory.setIsParent(false);
	    contentCategory.setSortOrder(1);
	    contentCategory.setStatus(1);
	    this.contentCategoryService.save(contentCategory);
	    // 判断上一节点是否为父节点，如果不是父节点修改为父节点
	    ContentCategory parentCategory = this.contentCategoryService.queryById(contentCategory.getParentId());
	    if (!parentCategory.getIsParent()) { // 不是父节点设为父节点
		parentCategory.setIsParent(true);
		this.contentCategoryService.updateSelective(parentCategory);
	    }
	    return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Title updateContentCategory
     * @Description 重命名节点名称 更新
     * @param contentCategory
     * @return ResponseEntity<Void>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
	try {
	    this.contentCategoryService.updateSelective(contentCategory);
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * @Title delete
     * @Description 删除节点 ，该节点的上一节点改为子节点，删除该节点所有子节点
     * @param contentCategory
     * @return ResponseEntity<Void>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(ContentCategory contentCategory) {
	try {
	    // 查找所有的子节点
	    List<Object> idS = new ArrayList<Object>();
	    idS.add(contentCategory.getId());
	    findAllSunNode(contentCategory.getId(), idS);
	    // 删除所有的子节点
	    this.contentCategoryService.delectByIds(ContentCategory.class, "id", idS);
	    // 判断当前节点的父节点是否还有其他的节点,如果没有修改isParent为false
	    ContentCategory record = new ContentCategory();
	    record.setParentId(contentCategory.getParentId());
	    List<ContentCategory> contentCategoryList = this.contentCategoryService.queryListByWhere(record);
	    if(null == contentCategoryList || contentCategoryList.isEmpty()) { // 说明该节点的父节点没有其他子节点，可以设置父节点为子节点
		ContentCategory parentCategory = new ContentCategory();
		parentCategory.setId(contentCategory.getParentId());
		parentCategory.setIsParent(false);
		this.contentCategoryService.updateSelective(parentCategory);
	    }
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * @Title findAllSunNode
     * @Description 根据parentId查找该ID的所有子节点(递归查找子节点)
     * @param parentId
     * @param list void
     * @throws 
     */
    public void findAllSunNode(Long parentId, List<Object> idS) {
	ContentCategory record = new ContentCategory();
	record.setParentId(parentId);
	List<ContentCategory> contentCategoryList = this.contentCategoryService.queryListByWhere(record);
	for (ContentCategory contentCategory : contentCategoryList) {
	    idS.add(contentCategory.getId());
	    // 判断该节点是否为父节点，如果是则继续找
	    if(contentCategory.getIsParent()){
		findAllSunNode(contentCategory.getId(), idS);
	    }
	}
    }
}
