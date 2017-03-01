package com.taotao.web.threadLocal;

import com.taotao.web.bean.User;

/**
 * @ClassName UserThreadLocal
 * @Author volc
 * @Description 当前线程处理数据统一问题，防止重复查询
 * @Date 2017年3月1日 下午2:18:07
 */
public class UserThreadLocal {
    public static final ThreadLocal<User> LOCAL = new ThreadLocal<User>();
    
    public static void set(User user){
	LOCAL.set(user);
    }
    
    public static User get(){
	return LOCAL.get();
    }

}
