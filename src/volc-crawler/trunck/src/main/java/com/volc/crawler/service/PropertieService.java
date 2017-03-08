package com.volc.crawler.service;

import org.springframework.stereotype.Service;
import com.volc.crawler.spring.PropertyConfig;

@Service
public class PropertieService {

    @PropertyConfig
    public String IMAGE_DIR;

    @PropertyConfig
    public String MAX_POOL_SIZE;

}
