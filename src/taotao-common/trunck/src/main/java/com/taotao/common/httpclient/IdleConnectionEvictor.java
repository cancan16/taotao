package com.taotao.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * @ClassName IdleConnectionEvictor
 * @Author volc
 * @Description 定期关闭绑定线程的httpclient对象
 * @Date 2017年2月27日 下午8:20:22
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager connMgr;

    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
	this.connMgr = connMgr;
	// 启动当前线程
	this.start();
    }

    @Override
    public void run() {
	try {
	    while (!shutdown) {
		synchronized (this) {
		    wait(5000);
		    // 关闭失效的连接
		    connMgr.closeExpiredConnections();
		}
	    }
	} catch (InterruptedException ex) {
	    // 结束
	}
    }

    public void shutdown() {
	shutdown = true;
	synchronized (this) {
	    notifyAll();
	}
    }
}
