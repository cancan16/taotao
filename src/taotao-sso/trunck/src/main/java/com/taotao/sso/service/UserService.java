package com.taotao.sso.service;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;

/**
 * @ClassName UserService
 * @Author volc
 * @Description 用户信息处理
 * @Date 2017年2月28日 下午4:57:59
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @MethodName check
     * @Author volc
     * @Description 检测用户信息
     * @Date 2017年2月28日 下午5:09:18
     */
    public Boolean check(String param, Integer type) {
	if (type < 1 || type > 3) {
	    return null;
	}
	// 查询
	User record = new User();
	switch (type) {
	case 1:
	    record.setUsername(param);
	    break;
	case 2:
	    record.setPhone(param);
	    break;
	case 3:
	    record.setEmail(param);
	    break;
	default:
	    break;
	}
	return this.userMapper.selectOne(record) == null;
    }

    /**
     * @MethodName saveUser
     * @Author volc
     * @Description 注册用户
     * @Date 2017年2月28日 下午6:19:54
     */
    public Boolean saveUser(User user) {
	user.setId(null);
	user.setCreated(new Date());
	user.setUpdated(user.getCreated());
	user.setPassword(DigestUtils.md5Hex(user.getPassword()));
	return this.userMapper.insert(user) == 1;
    }

    /**
     * @throws Exception 
     * @MethodName doLogin
     * @Author volc
     * @Description 登录逻辑处理
     * @Date 2017年2月28日 下午8:28:22
     */
    public String doLogin(String userName, String passWord) throws Exception {
	User record = new User();
	record.setUsername(userName);
	User user = this.userMapper.selectOne(record);
	if (null == user) {
	    return null;
	}
	if (!(StringUtils.equals(user.getPassword(), DigestUtils.md5Hex(passWord)))) {
	    return null;
	}
	// 登录成功， 生成token
	String token = DigestUtils.md5Hex(System.currentTimeMillis() + userName);
	// 将用户的数据保存到redis中， 并不是缓存中
	this.redisService.set("TOKEN_" + token, MAPPER.writeValueAsString(user), 60 * 30);
	return token;
    }
}
