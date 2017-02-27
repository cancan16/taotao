package com.taotao.web.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName Item
 * @Author volc
 * @Description 扩展的Item对象中没有的属性
 * @Date 2017年2月27日 下午11:35:40
 */
public class Item extends com.taotao.manage.pojo.Item {
    /**
     * @MethodName getImages
     * @Author volc
     * @Description 判断Image是否为空不为空转为String数组
     * @Date 2017年2月27日 下午11:39:21
     */
    public String[] getImages() {
	/*
	 * if (StringUtils.isEmpty(super.getImage())) { return null; }
	 */
	// split效率比较低，可以正则表达式分割
	// return super.getImage().split(",");
	// 使用StringUtils中的split分割效率高
	return StringUtils.split(super.getImage(), ","); // 如果super.getImage()为空就直接返回null
    }
}
