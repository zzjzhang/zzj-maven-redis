package com.core.controller;

import redis.clients.jedis.Jedis;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 * Controller
 * 
 * @author zsh10649
 *
 */
@RestController
@RequestMapping(value = "controller1")
public class Controller1 {

	//
	private Jedis jedis = new Jedis("192.168.137.2", 6379);



	// 用户登录 缓存 cookie
	@PostMapping(value = "map1")
	public String map1(@RequestParam(name = "userId") String userId,
			           @RequestParam(name = "name") String name,
			           @RequestParam(name = "role") String role) {
		String key = "user:" + userId;

		jedis.hset(key, "name", name);
		jedis.hset(key, "role", role);

		return "1";
	}



	// 缓存 用户浏览历史，可以用来数据分析
	@PostMapping(value = "map2")
	public String map2(HttpServletRequest httpServletRequest) {
		return null;
	}



	// 缓存 用户购物车，可以用来数据分析
	@PostMapping(value = "map3")
	public String map3(HttpServletRequest httpServletRequest) {
		return null;
	}





}