package com.core.task;

import redis.clients.jedis.Jedis;



/**
 * Redis 服务器 的 主从配置
 * 
 * @author zzj
 *
 */
public class MasterSlave implements Runnable {

	// 字段
	private String masterHost;
	private int masterPort;
	private String slaveHost;
	private int slavePort;
	private Jedis jedis;


	// 构造方法
	public MasterSlave(String masterHostName, int masterPortNo, String slaveHostName, int slavePortNo) {
		this.masterHost = masterHostName;
		this.masterPort = masterPortNo;
		this.slaveHost = slaveHostName;
		this.slavePort = slavePortNo;
		this.jedis = new Jedis(slaveHost, slavePort);
	}


	// 业务方法
	public void run() {

		// 设置 主从
		jedis.slaveof(masterHost, masterPort);

		// 取消 主从
		jedis.slaveofNoOne();

		// 查看 Redis 服务器 状态
		String redisInfo = jedis.info();
		System.out.println("Redis 服务器 信息：" + redisInfo);

	}

}