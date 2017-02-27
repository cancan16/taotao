package com.taotao.manage.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

/**
 * @ClassName ContentMapper
 * @Description 首页广告内容操作
 * @Author youtanzhi
 * @Date 2017年2月21日 下午8:42:01
 */
public interface ContentMapper extends Mapper<Content> {
    /**
     * @Title queryList
     * @Description 根据分类ID查询广告内容
     * @param categoryId
     * @return List<Content>
     * @throws 
     */
    List<Content> queryList(Long categoryId);
}
