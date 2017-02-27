package com.taotao.manage.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;

@RequestMapping("item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService catService;

    /**
     * @Title queryItemCatListByParentId
     * @Description 查询京东商品列表名称
     * @param parentId
     * @return ResponseEntity<List<ItemCat>>
     * @throws 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value = "id", defaultValue= "0") Long parentId) {
	try {
//	    List<ItemCat> list = catService.queryItemCatListByParentId(parentId);
	    ItemCat itemCat = new ItemCat();
	    itemCat.setParentId(parentId);
	    List<ItemCat> list = catService.queryListByWhere(itemCat);
	    if (list == null || list.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(list);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
