package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.taotao.common.service.RedisService;
import com.taotao.web.service.ItemService;

/**
 * @ClassName ItemCacheController
 * @Author volc
 * @Description 前台系统需要对后台提供一个接口，用于后台调用这个接口处理修改过的缓存，需要删除以前的缓存数据
 * @Date 2017年2月28日 下午2:10:43
 */
@RequestMapping("item/cache")
@Controller
public class ItemCacheController {
    @Autowired
    private RedisService redisService;

    /**
     * @MethodName deleteCache
     * @Author volc
     * @Description 删除缓存数据
     * @Date 2017年2月28日 下午2:12:09
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCache(@PathVariable("itemId") Long itemId) {
	try {
	    String key = ItemService.REDIS_KEY + itemId;
	    this.redisService.del(key);
	    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
