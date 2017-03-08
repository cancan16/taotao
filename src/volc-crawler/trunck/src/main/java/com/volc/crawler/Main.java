package com.volc.crawler;

import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.volc.crawler.thread.ThreadPool;

/**
 * @ClassName Main
 * @Author volc
 * @Description 爬虫程序入口
 * @Date 2017年3月8日 下午1:02:04
 * @version V1.0
 */
public class Main {

    public static ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
	applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext*.xml");
	// 从Spring容器中获取到所有可以执行的爬虫,并且放到线程池中执行
	Map<String, Crawler> map = applicationContext.getBeansOfType(Crawler.class);
	// 循环执行可执行的爬虫，进行爬去数据，存入到数据库中
	for (Crawler crawler : map.values()) {
	    ThreadPool.runInThread(crawler);
	}
    }

}
