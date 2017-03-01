package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * @ClassName SearchController
 * @Author volc
 * @Description 操作solr索引
 * @Date 2017年3月1日 下午8:11:21
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * @MethodName search
     * @Author volc
     * @Description 搜索
     * @Date 2017年3月1日 下午8:15:17
     * @url http://search.taotao.com/search.html?q=tcl
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("q") String keyWords, @RequestParam(value = "page", defaultValue = "1") Integer page) {
	ModelAndView mv = new ModelAndView("search");
	try {
	    // 解决GET前段传入参数的编码格式为utf-8
	    keyWords = new String(keyWords.getBytes("ISO-8859-1"), "UTF-8");
	    // 搜索
	    SearchResult searchResult = this.searchService.search(keyWords, page);
	    mv.addObject("query", keyWords); // 标题(搜索的关键字)
	    mv.addObject("itemList", searchResult.getData()); // item列表
	    mv.addObject("page", page); // 当前页
	    int total = searchResult.getTotal().intValue();
	    int pages = total % SearchService.rows == 0 ? total / SearchService.rows : total / SearchService.rows + 1;
	    mv.addObject("pages", pages); // 总页数
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return mv;
    }
}
