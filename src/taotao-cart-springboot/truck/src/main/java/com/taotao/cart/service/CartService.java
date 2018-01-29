package com.taotao.cart.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;
import com.taotao.cart.bean.Item;
import com.taotao.cart.bean.User;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.userThredLocal.UserThreadLocal;

/**
 * @ClassName CartService
 * @Author volc
 * @Description 对购物侧逻辑的处理
 * @Date 2017年3月2日 下午6:31:31
 */
@Service
@Transactional
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    /**
     * @MethodName addItemToCart
     * @Author volc
     * @Description 添加到购物车
     * @Date 2017年3月2日 下午6:32:57
     */
    public void addItemToCart(Long itemId) {
	User user = UserThreadLocal.get();
	// 判断该商品是否存在购物车中
	Cart record = new Cart();
	record.setItemId(itemId);
	record.setUserId(user.getId());
	Cart cart = this.cartMapper.selectOne(record);
	if (null == cart) { // 购物车中不存在加入
	    Item item = this.itemService.queryItemById(itemId);
	    if (null == item) {
		// TODO 给出用户提示
		return;
	    }
	    cart = new Cart();
	    cart.setCreated(new Date());
	    cart.setUpdated(cart.getCreated());
	    cart.setItemImage(item.getImages()[0]);
	    cart.setItemPrice(item.getPrice());
	    cart.setItemTitle(item.getTitle());
	    cart.setItemId(item.getId());
	    cart.setNum(1); // TODO
	    cart.setUserId(user.getId());
	    this.cartMapper.insert(cart);
	} else { // 该商品存在购物车中数量要相加，默认为1 TODO
	    cart.setNum(cart.getNum() + 1);
	    cart.setUpdated(new Date());
	    this.cartMapper.updateByPrimaryKeySelective(cart);
	}
    }

    /**
     * @MethodName queryCartList
     * @Author volc
     * @Description 通过当前用户的Id查询该要用户的购物车商品
     * @Date 2017年3月2日 下午7:03:17
     */
    public List<Cart> queryCartList() {
	User user = UserThreadLocal.get();
	return this.queryCartList(user.getId());
    }

    /**
     * @MethodName updateNum
     * @Author volc
     * @Description 修改购物车最终购买的数量
     * @Date 2017年3月2日 下午9:15:07
     */
    public void updateNum(Long itemId, Integer num) {
	User user = UserThreadLocal.get();
	// 更新的数据对象
	Cart record = new Cart();
	record.setNum(num);
	record.setUpdated(new Date());
	// 更新的条件
	Example example = new Example(Cart.class);
	example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("userId", user.getId());
	this.cartMapper.updateByExampleSelective(record, example);
    }

    /**
     * @MethodName deleteItem
     * @Author volc
     * @Description 删除购物车商品
     * @Date 2017年3月2日 下午9:56:39
     */
    public void deleteItem(Long itemId) {
	Cart record = new Cart();
	record.setUserId(UserThreadLocal.get().getId());
	record.setItemId(itemId);
	this.cartMapper.delete(record);
    }

    /**
     * @MethodName queryCartList
     * @Author volc
     * @Description 根据用户ID查询购物车商品列表
     * @Date 2017年3月3日 上午12:57:20
     */
    public List<Cart> queryCartList(Long userId) {
	Example example = new Example(Cart.class);
	example.createCriteria().andEqualTo("userId", userId);
	example.setOrderByClause("created DESC"); // 根据创建时间倒叙排序
	return this.cartMapper.selectByExample(example);
    }

}
