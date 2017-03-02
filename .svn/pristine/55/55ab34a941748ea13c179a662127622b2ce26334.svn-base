package com.taotao.cart.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.taotao.cart.bean.User;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.cart.userThredLocal.UserThreadLocal;

/**
 * @ClassName CartController
 * @Author volc
 * @Description 用户对购物车操作
 * @Date 2017年3月2日 下午6:06:19
 */
@RequestMapping("cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartCookieService cartCookieService;

    /**
     * @MethodName addItemToCart
     * @Author volc
     * @Description 加入购物车
     * @Date 2017年3月2日 下午6:11:03
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response) {
	User user = UserThreadLocal.get();
	if (null == user) {
	    // 未登录状态
	    this.cartCookieService.addItemToCart(itemId, request, response);
	} else { // 登录状态
	    this.cartService.addItemToCart(itemId);
	}
	// 跳转重定向到购物车列表，就是调用下面的方法
	return "redirect:/cart/list.html";
    }

    /**
     * @MethodName showCartList
     * @Author volc
     * @Description 显示购物车列表
     * @Date 2017年3月2日 下午7:11:13
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView showCartList(HttpServletRequest request) { // 这里读取cookie是不需要response对象的
	User user = UserThreadLocal.get();
	ModelAndView mv = new ModelAndView("cart");
	List<Cart> cartList = null;
	if (null == user) {
	    // 未登录状态
	    cartList = this.cartCookieService.queryCartList(request);
	} else { // 登录状态
	    cartList = this.cartService.queryCartList();
	}
	return mv.addObject("cartList", cartList);
    }

    /**
     * @MethodName updateNum
     * @Author volc
     * @Description 修改购物车中单个商品的购买数量(最终购买的数量)
     * @Date 2017年3月2日 下午9:10:38
     */
    @RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num, HttpServletRequest request, HttpServletResponse response) {
	User user = UserThreadLocal.get();
	if (null == user) {
	    this.cartCookieService.updateNum(itemId, num, request, response);
	} else { // 登录状态
	    this.cartService.updateNum(itemId, num);
	}
	// 204没有内容的返回
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response) {
	User user = UserThreadLocal.get();
	if (null == user) {
	    this.cartCookieService.deleteItem(itemId, request, response);
	} else { // 登录状态
	    this.cartService.deleteItem(itemId);
	}
	// 204没有内容的返回
	return "redirect:/cart/list.html";
    }
}