package com.taotao.cart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.bean.Item;
import com.taotao.cart.pojo.Cart;
import com.taotao.common.utils.CookieUtils;

/**
 * @ClassName CartCookieService
 * @Author volc
 * @Description 处理用户未登录状态下的购物车
 * @Date 2017年3月1日 下午1:19:58
 */
@Service
public class CartCookieService {

    private static final String COOKIE_NAME = "TT_CART";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Integer COOKIE_TIME = 60 * 60 * 24 * 30 * 12;

    @Autowired
    private ItemService itemService;

    /**
     * @MethodName addItemToCart
     * @Author volc
     * @Description 加入购物车
     * @Date 2017年3月2日 下午10:12:36
     */
    public void addItemToCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
	List<Cart> carts = queryCartList(request);
	// 判断要添加到购物车的商品在购物车中是否存在
	Cart cart = null;
	for (Cart c : carts) {
	    if (c.getItemId().longValue() == itemId.longValue()) { // 商品在购物车中存在
		cart = c;
		break;
	    }
	}
	if (null == cart) { // 说明该商品在cookie中不存在，需要加入到购物车中，即加入到cookie中
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
	    carts.add(cart); // 添加到购物车中
	} else { // 说明该商品在购物车中存在
	    cart.setNum(cart.getNum() + 1);
	    cart.setUpdated(new Date()); // 这里是对象的引用即cart引用了carts中的c，不需要再次放入到集合中
	}
	// 将集合cats写入到cookie中
	try {
	    CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * @MethodName queryCartList
     * @Author volc
     * @Description 从cookie中读取购物车中的商品信息
     * @Date 2017年3月2日 下午10:50:57
     */
    public List<Cart> queryCartList(HttpServletRequest request) {
	// 根据cookie名称获取该cookie对应的购物车商品信息(json格式字符串)
	String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
	List<Cart> carts = null;
	if (StringUtils.isEmpty(cookieValue)) {
	    carts = new ArrayList<Cart>();
	} else {
	    try {
		// 反序列化为List<Cart>集合
		carts = MAPPER.readValue(cookieValue, MAPPER.getTypeFactory().constructCollectionLikeType(List.class, Cart.class));
	    } catch (Exception e) {
		e.printStackTrace();
		carts = new ArrayList<Cart>();
	    }
	}
	return carts;
    }

    /**
     * @MethodName updateNum
     * @Author volc
     * @Description 更新cookie既要读也要写
     * @Date 2017年3月2日 下午10:54:32
     */
    public void updateNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
	List<Cart> carts = queryCartList(request);
	// 判断要添加到购物车的商品在购物车中是否存在
	for (Cart c : carts) {
	    if (c.getItemId().longValue() == itemId.longValue()) { // 商品在购物车中存在
		c.setNum(num);
		c.setUpdated(new Date());
		break;
	    }
	}
	// 将集合cats写入到cookie中
	try {
	    CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * @MethodName deleteItem
     * @Author volc
     * @Description 删除购物车中的商品
     * @Date 2017年3月2日 下午10:57:41
     */
    public void deleteItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
	List<Cart> carts = queryCartList(request);
	// 判断要添加到购物车的商品在购物车中是否存在
	for (Cart c : carts) {
	    if (c.getItemId().longValue() == itemId.longValue()) { // 商品在购物车中存在
		carts.remove(c);
		break;
	    }
	}
	// 将集合cats写入到cookie中
	try {
	    CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts), COOKIE_TIME, true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
