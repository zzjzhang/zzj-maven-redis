package com.core.task;

import java.util.List;
import java.util.LinkedList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * 
 * 提示 最近 联系的 100 人
 * 
 * 使用 列表 结构，因为 列表 是 有序的
 * 
 * @author zsh10649
 *
 */
public class Task12 implements Runnable {

	// 1. Fields
	private String host;
	private int port;
	private Jedis jedis;
	private Pipeline pipeline;

	private String listKey;
	private String contact;


	// 2. Construct Method
	public Task12(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.listKey = "contactList";
		this.contact = "contactor_1";
	}


	// 3. Business Method
	public void run() {
		// 1. 添加 联系人
		this.pipeline = this.jedis.pipelined();

		// 移除 已经 存在 的 这位 联系人
		pipeline.lrem(listKey, 0, contact);

		// 把 这位 联系人 添加 到 列表 头部
		pipeline.lpush(listKey, contact);

		// 把 列表 长度 限定 为 100
		pipeline.ltrim(listKey, 0, 99);

		// 执行 事务
		pipeline.exec();

		// 2. 查询 提示 联系人
		String input = "";
		List<String> contactList = jedis.lrange(listKey, 0, -1);
		List<String> returnList = new LinkedList<String>();

		for(String contact : contactList) {
			if(contact.startsWith(input)) {
				returnList.add(contact);
			}
		}

		System.out.println(returnList);
	}


}