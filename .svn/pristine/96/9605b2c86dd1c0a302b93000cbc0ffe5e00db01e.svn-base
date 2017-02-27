package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

    /**
     * Spring容器 -- 父容器 SpringMVC容器 -- 子容器 父子容器的关系： 1 子容器能够访问父容器的资源（bean）
     * a)示例：Controller可以注入Service 2 父容器不能访问子容器的资源（bean）
     * value注解用法：spring容器初始化完成后(加载所有的bean之后)，在当前容器中获取值，然后再注入
     * 使用spring提供的加载配置文件功能获取常量值
     * 容器加载时spring在扫描evn.perproties配置文件(需要在applicationContext.xml中配置加载)
     * 使用Value注解获取配置文件中值
     */
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
