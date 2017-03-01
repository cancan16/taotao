package com.taotao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.User;

/**
 * @ClassName UserService
 * @Author volc
 * @Description 用户信息处理
 * @Date 2017年3月1日 下午1:19:58
 */
@Service
public class UserService {

    @Autowired
    private ApiService apiService;

    @Autowired
    public PropertiesService propertiesService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @MethodName queryUserByToken
     * @Author volc
     * @Description TODO
     * @Date 2017年3月1日 下午1:20:40
     */
    public User queryUserByToken(String token) {
	try {
	    String url = propertiesService.SSO_TAOTAO_URL + "/service/user/" + token;
	    String jsonData = this.apiService.doGet(url);
	    if (StringUtils.isNotEmpty(jsonData)) {
		return MAPPER.readValue(jsonData, User.class);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
}
