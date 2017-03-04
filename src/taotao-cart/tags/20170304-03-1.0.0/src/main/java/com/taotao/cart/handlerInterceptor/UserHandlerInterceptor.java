package com.taotao.cart.handlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.taotao.cart.bean.User;
import com.taotao.cart.service.UserService;
import com.taotao.cart.userThredLocal.UserThreadLocal;
import com.taotao.common.utils.CookieUtils;

/**
 * @ClassName UserLoginHandlerInterceptor
 * @Author volc
 * @Description 拦截器，用于判断用户是否登录，在淘淘-web-servlet.xml文件中配置拦截器
 * @Date 2017年3月1日 下午1:03:17
 */
public class UserHandlerInterceptor implements HandlerInterceptor {
    
    public static final String COOKIE_NAME = "TT_TOKEN";

    @Autowired
    private UserService userService;
    
    @Override  // 前置方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
	 UserThreadLocal.set(null); // 清空当前线程的user对象
	// 获取cookie中的token
	String cookieToken = CookieUtils.getCookieValue(request, COOKIE_NAME);
	if(StringUtils.isEmpty(cookieToken)) { // 如果cookieToken为空说明未登录
	    // 未登录状态也要放行
	    return true;
	}
	User user = this.userService.queryUserByToken(cookieToken);
	if(null == user) {
	 // 未登录状态也要放行
	    return true;
	}
	// 处于登录状态
	UserThreadLocal.set(user); // 处于登录状态将用户信息放置到当前线程中
	return true; // 放行
    }

    @Override // 处理方法
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	
    }

    @Override // 后置方法
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {
    }

}
