package cn.itcast.quartz;

/**
 * @ClassName TestThread
 * @Author volc
 * @Description 需要执行的线程类
 * @Date 2017年4月10日 上午12:55:36
 */
public class TestThread implements Runnable {
    public void run() {
	System.out.println("执行了线程");
    }
}
