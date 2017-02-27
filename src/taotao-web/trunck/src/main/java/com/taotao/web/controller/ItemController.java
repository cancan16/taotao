package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;

/**
 * @ClassName ItemController
 * @Author volc
 * @Description 商品详情操作
 * @Date 2017年2月27日 下午10:57:09
 */
@RequestMapping("item")
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    /**
     * @MethodName showDetail
     * @Author volc
     * @Description 根据商品ID查询商品详情
     * @Date 2017年2月27日 下午10:58:35
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("itemId") Long itemId) {
	// item名称和view页面文件名称一样
	ModelAndView mv = new ModelAndView("item");
	Item item = this.itemService.queryItemById(itemId);
	return mv.addObject("item", item);
    }
}
