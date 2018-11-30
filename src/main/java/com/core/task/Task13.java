package com.core.task;

import redis.clients.jedis.Jedis;

/**
 * 
 * 分布式锁
 * 
 * @author zzj
 *
 */
public class Task13 implements Runnable {

	// 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String lockKey;

	// 构造方法
	public Task13(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.lockKey = "lockKey";
	}

	// 业务方法
	public void run() {
		// 尝试 获取 锁
		Long setResult = jedis.setnx(lockKey, "lockValue");

		if(setResult == 1) {
			// 如果 获取到 锁

			// 设置 锁 超时 时间
			jedis.expire(lockKey, 5 * 1000);

			// 执行 业务逻辑
			// ...

			// 删除 锁
			jedis.del(lockKey);
		} else {
			// 如果 没有 获取到 锁
			// 检查 锁 ：如果 锁 没有设置 超时时间，则 设置 超时时间
			Long timeToLive = jedis.ttl(lockKey);

			if(timeToLive == -1) {
				jedis.expire(lockKey, 5 * 1000);
			}

			// 线程 休眠
			try {
				Thread.sleep(5 * 1000);
			} catch (InterruptedException e) {
				
			}

			// 循环 尝试 再次 获取 锁
			while(Boolean.TRUE) {
				
			}
		}
	}
}