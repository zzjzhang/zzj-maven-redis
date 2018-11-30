package com.core.task;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 *
 * 非事务性 流水线
 *
 * @author zzj
 *
 */
public class Task11 implements Runnable {
	// 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String hashKey;
	private String hashField;


	// 构造方法
	public Task11(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.hashKey = "hashKey1";
		this.hashField = "hashField1";
	}


	// 业务方法
	public void run() {
		Pipeline pipeline = this.jedis.pipelined();

		// 设置 HASH 值
		pipeline.hset(hashKey, hashField, "hashValue1");

		// 删除 HASH 键
		pipeline.del(hashKey);

		// 执行
		pipeline.exec();
	}
}