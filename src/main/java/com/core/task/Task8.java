package com.core.task;

import redis.clients.jedis.Jedis;

/**
 * 键 的 过期时间
 * 
 * @author zsh10649
 *
 */
public class Task8 implements Runnable {

	// 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String key;


	// 构造方法
	public Task8(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.key = "key";
	}


	// 业务方法
	public void run() {

		// 设置 键 值
		jedis.set(key, "value");
		System.out.println("设置 " + key + " 的值为：" + jedis.get(key));

		// 设置 键 在 过了 指定 时间段 后 过期
		jedis.expire(key, 10);
		System.out.println("设置 键 " + key + " 在 指定时间段 " + 10 + " 秒 后 过期");

		// 设置 键 在 过了 指定 时间点 后 过期
		//jedis.expireAt(key, 139600);

		// 线程 休眠
		try {
			System.out.println("线程休眠...");
			Thread.sleep(5 * 1000);
		} catch(Exception e) {
			
		}

		// 获取 键 的 剩余 生存时间
		Long liveSeconds = jedis.ttl(key);
		System.out.println("键 " + key + " 剩余 生存时间：" + liveSeconds + " 秒");

		// 取消 键 的过期时间
		jedis.persist(key);

		// 获取 键 的 值
		String value = jedis.get(key);
		System.out.println("键 " + key + " 的 值 为：" + value);

		// 删除 键
		jedis.del(key);

	}

}