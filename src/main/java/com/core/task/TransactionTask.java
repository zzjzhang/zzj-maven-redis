package com.core.task;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.List;

/**
 * 
 * Redis 事务
 * 
 * @author zzj
 *
 */
public class TransactionTask implements Runnable {
	// 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String strKey;

	// 构造方法
	public TransactionTask(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.strKey = "strKey";
	}

	// 业务方法
	public void run() {
		// 开启 事务
		Pipeline pipeline = jedis.pipelined();
		pipeline.multi();

		// 事务 操作一
		pipeline.set(strKey, "1");
		//System.out.println("strKey 值：操作一：" + pipeline.get(strKey).get());

		// 事务 操作二
		pipeline.incrBy(strKey, 2);
		//System.out.println("strKey 值：操作二：" + pipeline.get(strKey).get());

		// 执行 事务
		Response<List<Object>> response = pipeline.exec();

		// 关闭 事务
		pipeline.close();

		// 执行 事务 后
		System.out.println("strKey 值：执行事务后：" + jedis.get(strKey));

		// 删除
		jedis.del(strKey);
	}
}
