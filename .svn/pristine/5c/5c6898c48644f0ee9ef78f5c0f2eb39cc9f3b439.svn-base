package cn.itcast.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName ContextUtil
 * @Author volc
 * @Description 获取ApplicationContext对象工具类，用于方取出注入的bean
 * @Date 2017年4月10日 上午12:08:58
 */
public class ContextUtil {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
	return context;
    }

    public static Object getBean(String beanName) {
	return context.getBean(beanName);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	context = applicationContext;
    }

    // 启动调度器
    public static void main(String[] args) {
	context = new ClassPathXmlApplicationContext(new String[] { "applicationContext-schedulerThread.xml" });
    }

}
