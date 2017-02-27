package com.taotao.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.abel533.entity.Example;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;

/**
 * @ClassName ItemParamItemService
 * @Description 商品规格参数操作
 * @Author youtanzhi
 * @Date 2017年2月11日 下午10:42:13
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    /**
     * @Title udpateItemParamItem
     * @Description 根据商品ID更新该商品的规格参数
     * @param id
     * @param itemParams void
     * @throws 
     */
    public void udpateItemParamItem(Long itemId, String itemParams) {
	// 更新的数据
	ItemParamItem itemParamItem = new ItemParamItem();
	itemParamItem.setParamData(itemParams);
	itemParamItem.setUpdated(new Date());
	// 更新的条件
	Example example = new Example(ItemParamItem.class);
	example.createCriteria().andEqualTo("itemId", itemId);// 要和ItemParamItem实体类字段名一致
	this.itemParamItemMapper.updateByExampleSelective(itemParamItem, example);// 做选择行的更新
	
    }
}
