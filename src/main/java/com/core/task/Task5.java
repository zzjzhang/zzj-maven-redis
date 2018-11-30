package com.core.task;

import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;



/**
 * ZSET
 * 
 * 1. 存储键值对
 * 
 * @author zsh10649
 *
 */
public class Task5 implements Runnable {

	// 1. 字段
	private String host;
	private int port;
	private Jedis jedis;
	private String zsetKey;


	// 2. 构造方法
	public Task5(String hostName, int portNo) {
		this.host = hostName;
		this.port = portNo;
		this.jedis = new Jedis(host, port);
		this.zsetKey = "zsetKey";
	}


	// 3. 业务方法
	public void run() {

		// 1. 向 ZSET 添加值
		jedis.zadd(zsetKey, 5.98, "member_2");
		jedis.zadd(zsetKey, 1.98, "member_1");
		jedis.zadd(zsetKey, 9.98, "member_3");

		// 2. 获取 ZSET 一定范围的值，且按照 分数 排序 （分值 从小到大）
		Set<Tuple> tupleSet_1 = jedis.zrangeWithScores(zsetKey, 0, -1);

		for(Tuple tuple : tupleSet_1) {
			String element = tuple.getElement();
			double score = tuple.getScore();

			System.out.println("small to big - element: " + element + ", " + "score: " + score);
		}

		System.out.println("");

		// 3. 获取 ZSET 一定范围的值，且按照 分数 排序 （分值 从大到小）
		Set<Tuple> tupleSet_2 = jedis.zrevrangeWithScores(zsetKey, 0, -1);

		for(Tuple tuple : tupleSet_2) {
			String element = tuple.getElement();
			double score = tuple.getScore();

			System.out.println("big to small - element: " + element + ", " + "score: " + score);
		}

		System.out.println("");

		// 4. 按 分数 范围，获取 ZSET 的值
		Set<Tuple> tupleSet_3 = jedis.zrangeByScoreWithScores(zsetKey, 0.98, 6.98);

		for(Tuple tuple : tupleSet_3) {
			String element = tuple.getElement();
			double score = tuple.getScore();

			System.out.println("score range - element: " + element + ", " + "score: " + score);
		}

		System.out.println("");

		// 5. 删除 ZSET 中的成员
		jedis.zrem(zsetKey, "member_2");

		Set<Tuple> tupleSet_4 = jedis.zrangeWithScores(zsetKey, 0, -1);

		for(Tuple tuple : tupleSet_4) {
			String element = tuple.getElement();
			double score = tuple.getScore();

			System.out.println("element: " + element + ", " + "score: " + score);
		}

		System.out.println("");

		// 获取 ZSET 中成员 数量
		Long zNums = jedis.zcard(zsetKey);
		System.out.println("zset 中成员数量：" + zNums);

		// 获取 ZSET 中成员 排名（分值 从小到大）
		Long zRank = jedis.zrank(zsetKey, "member_3");
		System.out.println("zset 中 member_3 成员排名（分值 从小到大）：" + zRank);

		// 获取 ZSET 中成员 排名（分值 从大到小）
		Long zRevRank = jedis.zrevrank(zsetKey, "member_3");
		System.out.println("zset 中 member_3 成员排名（分值 从大到小）：" + zRevRank);

		System.out.println("");

		// 删除 ZSET
		jedis.del(zsetKey);
	}

}