package com.core.task;

import redis.clients.jedis.Jedis;

/**
 * 使用 LIST 存储 待执行 任务
 * 1. 异步执行任务
 * 2. 先进先出
 * 
 * @author zzj
 */
public class Task14 implements Runnable {

	// 1. Fields
	private String host;
	private int port;
	private Jedis jedis;
	private String listKey;

	// 2. Construct Method
	public Task14(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.listKey = "TASKKEY";
	}

	// 3. Business Method
	public void run() {
		

	}

	// 3-1) 推入 待执行 任务
	private void pushTask(String task) {
		jedis.lpush(listKey, task);
	}

	// 3-2) 获取 待执行 任务
	private void takeTask() {
		String task = jedis.rpop(listKey);
		handleTask(task);
	}

	// 3-3) 执行 任务
	private void handleTask(String task) {
		// ...
	}
}