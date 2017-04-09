package cn.itcast.quartz;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MyJobThread {
    public void service() {
	ThreadPoolTaskExecutor jobPropertyConfigurer = (ThreadPoolTaskExecutor) ContextUtil.getBean("executor");
	jobPropertyConfigurer.execute(new TestThread());
    }
}
