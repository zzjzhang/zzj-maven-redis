package com.core.task;

import java.util.List;
import redis.clients.jedis.Jedis;

/**
 * 
 * 其他命令 —— 排序
 * 
 * @author zsh10649
 *
 */
public class Task6 implements Runnable {

	// 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String listKey;

	// 构造方法
	public Task6(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.listKey = "listKey";
	}

	// 业务方法
	public void run() {
		// 列表
		jedis.rpush(listKey, "3", "15", "6", "25");
		List<String> list = jedis.lrange(listKey, 0, -1);
		System.out.println("排序前 列表：" + list.toString());

		list = jedis.sort(listKey);
		System.out.println("排序后 列表：" + list.toString());

		// 删除
		jedis.del(listKey);
	}

}