package com.core.task;

import redis.clients.jedis.Jedis;

/**
 * STRING
 * 
 * @author zzj
 */
public class StringTask implements Runnable {

	// 1. 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String strKey;
	private String statusCode;


	// 2. 构造方法
	public StringTask(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.strKey = "strKey";
	}


	// 3. 业务方法
	public void run() {

		// 设置字符串的值
		statusCode = jedis.set(strKey, "mike");
		System.out.println("'set' status code: " + statusCode);

		// 获取字符串的值
		String value = jedis.get(strKey);
		System.out.println("'get' value: " + value);

		// 对 字符串 存储的 数字 加 1 （原子性）
		jedis.set(strKey, "666");
		jedis.incr(strKey);
		System.out.println(strKey + " 增加 1 后的值为：" + jedis.get(strKey));

		// 对 字符串 存储的 数字 加 固定值 （原子性）
		jedis.incrBy(strKey, 3);
		System.out.println(strKey + " 增加 3 后的值为：" + jedis.get(strKey));

		// 对 字符串 存储的 数字 减 1 （原子性）
		jedis.decr(strKey);
		System.out.println(strKey + " 减去 1 后的值为：" + jedis.get(strKey));

		// 对 字符串 存储的 数字 减 固定值 （原子性）
		jedis.decrBy(strKey, 3);
		System.out.println(strKey + " 减去 3 后的值为：" + jedis.get(strKey));

		// 对 字符串 存储的 浮点数 增加 浮点数
		jedis.incrByFloat(strKey, 1.1);
		System.out.println(strKey + " 增加 1.1 后的值为：" + jedis.get(strKey));

		// 对 字符串 存储的 值 追加 内容
		jedis.set(strKey, "good");
		jedis.append(strKey, " morning... ");
		System.out.println(strKey + " 添加字段后的内容为：" + jedis.get(strKey));

		// 获取 字符串 中 一部分 内容
		String value1 = jedis.getrange(strKey, 2, 6);
		System.out.println(strKey + "部分内容为：" + value1);

		// 删除字符串
		jedis.del(strKey);

	}

}