package com.core.task;

import java.util.Set;
import redis.clients.jedis.Jedis;

/**
 * 多个客户端 监听 一个 队列
 * 
 * 《实战》例子
 * - 聊天群 P141 （实体：聊天群、用户、消息）
 * 
 * @author zzj
 */
public class Task15 implements Runnable {

	// 1. 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String groupKey;
	private String userKey;
	private String msgKey;

	// 2. 构造方法
	public Task15(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.groupKey = "group:";
		this.userKey = "user:";
		this.msgKey = "msg:";
	}

	// 3. 业务方法
	public void run() {
		// 加入群
		joinGroup(null, null);

		// 发送消息
		sendMsg(null, null, 0, null);

		// 获取消息
		recMsg(null);

		// 退出群
		quitGroup(null, null);
	}



	// 加入聊天群
	private void joinGroup(String groupId, String userId) {
		// 更新 聊天群 zset
		jedis.zadd(groupKey + groupId, 0, userId);

		// 更新 用户 zset
		jedis.zadd(userKey + userId, 0, groupId);
	}

	// 退出 聊天群
	private void quitGroup(String groupId, String userId) {
		// 更新 聊天群 zset
		jedis.zrem(groupKey + groupId, userId);

		// 更新 用户 zset
		jedis.zrem(userKey + userId, groupId);
	}

	// 发布 一条 消息
	private void sendMsg(String groupId, String userId, int msgId, String message) {
		// 更新 消息 zset
		jedis.zadd(msgKey + groupId, msgId, message);

		// 更新 聊天群 zset
		jedis.zadd(groupKey + groupId, msgId, userId);

		// 更新 用户 zset
		jedis.zadd(userKey + userId, msgId, groupId);
	}

	// 获取 最新 消息
	private void recMsg(String userId) {
		// 获取 用户 所属 组
		Set<String> groupIds = jedis.zrange(userKey + userId, 0, -1);

		// 获取 该组 所有 消息
		for(String groupId : groupIds) {
			jedis.zrange(msgKey + groupId, 0, -1);
		}
	}



	public static void main(String[] args) {
		
	}

}