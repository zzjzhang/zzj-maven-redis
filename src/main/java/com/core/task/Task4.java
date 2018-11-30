package com.core.task;

import java.util.Map;
import java.util.List;
import redis.clients.jedis.Jedis;

/**
 * 
 * HASH
 * 1. 存储多个键值对之间的映射
 * 
 * @author zzj
 *
 */
public class Task4 implements Runnable {

	// 1. 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String hashKey;


	// 2. 构造方法
	public Task4(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.hashKey = "hashKey";
	}


	// 3. 业务方法
	public void run() {
		// 1. 设置 HASH 值
		jedis.hset(hashKey, "hashField1", "hashValue1");
		jedis.hset(hashKey, "hashField2", "hashValue2");
		jedis.hset(hashKey, "hashField3", "hashValue3");
		jedis.hset(hashKey, "hashField1", "hashValue4");
		System.out.println("添加 hash 后：" + jedis.hgetAll(hashKey));
		System.out.println("");

		// 2. 获取 HASH 值
		String hashValue1 = jedis.hget(hashKey, "hashField1");
		System.out.println("获取 hashField1: " + hashValue1);
		System.out.println("");

		// 3. 删除 HASH 值
		System.out.println("删除 hashField2 前 Hash: " + jedis.hgetAll(hashKey));
		jedis.hdel(hashKey, "hashField2");
		System.out.println("删除 hashField2 后 Hash: " + jedis.hgetAll(hashKey));
		System.out.println("");

		// 4. 读取所有 HASH
		Map<String, String> hash = jedis.hgetAll(hashKey);

		for(String key : hash.keySet()) {
			System.out.println("HASH values: " + hash.get(key));
		}

		System.out.println("");

		// 获取 HASH 成员数量
		Long hashLen = jedis.hlen(hashKey);
		System.out.println("HASH 成员数量：" + hashLen);
		System.out.println("");

		// 获取 HASH 对应成员
		List<String> hashList = jedis.hmget(hashKey, "hashField1", "hashField3");

		for(String hashListValue : hashList) {
			System.out.println("HASH 对应成员：" + hashListValue);
		}

		System.out.println("");

		// 判断 键 是否存在于 HASH 中
		Boolean exists = jedis.hexists(hashKey, "hashField3");
		System.out.println("HASH 成员：" + jedis.hgetAll(hashKey));
		System.out.println("hashField3 是否存在于 HASH 中：" + exists);
		System.out.println("");

		// 5. 删除 HASH Key
		jedis.del(hashKey);
	}

}