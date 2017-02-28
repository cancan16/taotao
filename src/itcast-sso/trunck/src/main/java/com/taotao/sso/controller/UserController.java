package com.taotao.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

/**
 * @ClassName UserController
 * @Author volc
 * @Description 单点登录控制器
 * @Date 2017年2月28日 下午3:54:54
 * @url http://sso.taotao.com/user/register.html
 */
@RequestMapping("user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    public static final String COOKIE_NAME = "TT_TOKEN";

    /**
     * @MethodName toRegister
     * @Author volc
     * @Description 注册，加载注册页面
     * @Date 2017年2月28日 下午3:56:24
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRegister() {
	return "register";
    }

    /**
     * @MethodName toLogin
     * @Author volc
     * @Description 登录,加载登录页面
     * @Date 2017年2月28日 下午8:12:37
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin() {
	return "login";
    }

    /**
     * @MethodName check
     * @Author volc
     * @Description 检测用户信息
     * @Date 2017年2月28日 下午5:04:38
     */
    @RequestMapping(value = "check/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param, @PathVariable("type") Integer type) {
	try {
	    Boolean bool = this.userService.check(param, type);
	    if (null == bool) {
		// 400 请求失败
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	    // 为了兼容前端的业务逻辑做妥协进行取反返回
	    return ResponseEntity.ok(!bool);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// 500 系统错误
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /*
     * 使用hibernate-validatorjar包进行前台表单的校验，user对象为校验对象，校验结果用BindingResult对象接收
     */
    @ResponseBody // 返回的不是responseEntity,而是一个对象需要进行@ResponseBody注解
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult) { // 可用User对象直接接受多个参数
	Map<String, Object> result = new HashMap<String, Object>();
	// 处理校验结果
	if (bindingResult.hasErrors()) {
	    List<ObjectError> allErrors = bindingResult.getAllErrors();
	    List<String> msgs = new ArrayList<String>();
	    for (ObjectError error : allErrors) {
		String defaultMessage = error.getDefaultMessage(); // 定义的message内容
		msgs.add(defaultMessage);
	    }
	    result.put("status", "400");
	    result.put("data", StringUtils.join(msgs, "|"));
	    return result;
	}
	Boolean boo = this.userService.saveUser(user);
	if (boo) {
	    // 注册成功
	    result.put("status", "200");
	} else {
	    result.put("status", "300");
	    result.put("data", " 是的");
	}
	return result;
    }

    /**
     * @MethodName doLogin
     * @Author volc
     * @Description 登录请求处理
     * @Date 2017年2月28日 下午8:45:27
     */
    @ResponseBody
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public Map<String, Object> doLogin(@RequestParam("username") String userName, @RequestParam("password") String passWord, HttpServletRequest request, HttpServletResponse response) {
	Map<String, Object> result = new HashMap<String, Object>();
	try {
	    String token = this.userService.doLogin(userName, passWord);
	    if (null == token) {
		result.put("status", 400);
	    } else { // 登录成功后需要将token写入到cookie中
		result.put("status", 200);
		// cookie中有中文时需要编码，这里没有中文不用编码。
		// 可以设定cookie的生命周期，时间长可以实现关闭浏览器后再打开会自动登录
		CookieUtils.setCookie(request, response, COOKIE_NAME, token);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    result.put("status", 500);
	}
	return result;
    }
}
