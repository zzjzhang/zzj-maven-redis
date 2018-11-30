package com.core.task;

import java.util.List;
import redis.clients.jedis.Jedis;



/**
 * 
 * LIST
 * 
 * 1. 有序存储多个字符串
 * 2. 相同元素可以重复出现
 * 
 * @author zsh10649
 *
 */
public class Task2 implements Runnable {

	// 1. 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String listKey1;
	private String listKey2;



	// 2. 构造方法
	public Task2(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.listKey1 = "LIST_1";
		this.listKey2 = "LIST_2";
	}



	// 3. 业务方法
	public void run() {

		// 在左边推入值
		jedis.lpush(listKey1, "value_1");

		// 在右边推入值
		jedis.rpush(listKey1, "value_2");

		// 从左边推出值
		String lpopValue = jedis.lpop(listKey1);
		System.out.println("lpopValue: " + lpopValue);

		// 从右边推出值
		String rpopValue = jedis.rpop(listKey1);
		System.out.println("rpopValue: " + rpopValue);

		// 
		jedis.lpush(listKey1, "value_1");
		jedis.lpush(listKey1, "value_0");
		jedis.rpush(listKey1, "value_2");
		jedis.rpush(listKey1, "value_3");
		jedis.rpush(listKey1, "value_4");
		jedis.rpush(listKey1, "value_5");
		jedis.rpush(listKey1, "value_6");
		jedis.rpush(listKey1, "value_7");
		jedis.rpush(listKey1, "value_8");

		// 从左边获取一定范围内的值
		List<String> listValue = jedis.lrange(listKey1, 1, 2);

		for(String value : listValue) {
			System.out.println("list value: " + value);
		}

		// 从左边获取一定索引位置的值
		String value = jedis.lindex(listKey1, 1);
		System.out.println("value: " + value);

		// 从 左边 修剪 一定长度 的 列表
		jedis.ltrim(listKey1, 0, 3);
		System.out.println("修剪后：" + jedis.lrange(listKey1, 0, -1));

		// 从 列表 左边 阻塞地 弹出
		jedis.blpop(1000, listKey1);
		System.out.println("从左边阻塞地弹出后：" + jedis.lrange(listKey1, 0, -1));

		// 从 列表 右边 阻塞地 弹出
		jedis.brpop(1000, listKey1);
		System.out.println("从右边阻塞地弹出后：" + jedis.lrange(listKey1, 0, -1));

		// 从 一个 列表 阻塞地 移动数据到 另一个 列表
		jedis.brpoplpush(listKey1, listKey2, 1000);
		System.out.println("从右边移动数据到左边后：第一个列表：" + jedis.lrange(listKey1, 0, -1));
		System.out.println("从右边移动数据到左边后：第二个列表：" + jedis.lrange(listKey2, 0, -1));

		// 删除列表
		jedis.del(listKey1);
		jedis.del(listKey2);

	}

}