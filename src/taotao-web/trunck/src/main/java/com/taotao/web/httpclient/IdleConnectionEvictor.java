package com.taotao.web.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * @ClassName IdleConnectionEvictor
 * @Description 定期关闭绑定线程的httpclient对象
 * @Author youtanzhi
 * @Date 2017年2月25日 上午12:02:29
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
