package com.core.task;

import java.util.Set;
import redis.clients.jedis.Jedis;



/**
 * 
 * SET
 *
 * 1. 无序存储多个字符串
 * 2. 相同元素不能重复出现
 * 
 * @author zzj
 *
 */
public class SetTask implements Runnable {

	// 1. 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String setKey1;
	private String setKey2;
	private String setKey5;



	// 2. 构造方法
	public SetTask(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		jedis = new Jedis(host, port);
		this.setKey1 = "SET_1";
		this.setKey2 = "SET_2";
		this.setKey5 = "SET_5";
	}



	// 3. 业务方法
	public void run() {

		// 为 SET 添加值
		jedis.sadd(setKey1, "member_1");
		jedis.sadd(setKey1, "member_2");
		jedis.sadd(setKey1, "member_3");

		// 显示所有 SET 值
		Set<String> setMembers = jedis.smembers(setKey1);

		for(String member : setMembers) {
			System.out.println("添加后 Set value 1st: " + member);
		}

		System.out.println("");

		// 移除 SET 中的值
		jedis.srem(setKey1, "member_2");
		setMembers = jedis.smembers(setKey1);

		for(String member : setMembers) {
			System.out.println("移除后 Set value 2nd: " + member);
		}

		System.out.println("");

		// 判断是否 SET 成员
		boolean isMember = jedis.sismember(setKey1, "member_3");
		System.out.println("member_3 is member of set: " + isMember);
		System.out.println("");

		// 获取 SET 成员数量
		Long numOfMembers = jedis.scard(setKey1);
		System.out.println("Number of members: " + numOfMembers);
		System.out.println("");

		// 把 SET1 中的成员 移动到 SET2
		jedis.smove(setKey1, setKey2, "member_3");
		System.out.println("SET_1: " + jedis.smembers(setKey1));
		System.out.println("SET_2: " + jedis.smembers(setKey2));
		System.out.println("");

		// 差集
		jedis.sadd(setKey1, "member_2", "member_3");
		System.out.println("差集前：SET_1: " + jedis.smembers(setKey1));
		System.out.println("差集前：SET_2: " + jedis.smembers(setKey2));
		Set<String> setKey3 = jedis.sdiff(setKey1, setKey2);
		System.out.println("差集后：SET_1: " + setKey3.toString());
		System.out.println("");

		// 交集
		Set<String> setKey4 = jedis.sinter(setKey1, setKey2);
		System.out.println("交集后：" + setKey4.toString());
		System.out.println("");

		// 并集
		jedis.sadd(setKey5, "member_5");
		System.out.println("并集前：SET_1：" + jedis.smembers(setKey1));
		System.out.println("并集前：SET_5：" + jedis.smembers(setKey5));
		Set<String> setKey6 = jedis.sunion(setKey1, setKey5);
		System.out.println("并集后：" + setKey6.toString());

		// 删除 SET
		jedis.del(setKey1);
		jedis.del(setKey2);
		jedis.del(setKey5);

	}

}