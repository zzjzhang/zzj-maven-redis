package com.core.task;

import redis.clients.jedis.Jedis;

/**
 * 持久化 —— 快照、AOF
 * 
 * @author zzj
 *
 */
public class Task9 implements Runnable {

	// 字段
	private String host;
	private int port;
	private Jedis jedis;


	// 构造方法
	public Task9(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
	}


	// 业务方法
	public void run() {

		// 快照 ——
		// 同步 快照 持久化
		jedis.save();

		// 异步 快照 持久化
		jedis.bgsave();

		// AOF ——
		// 重写 / 压缩 AOF 文件
		jedis.bgrewriteaof();

	}

}