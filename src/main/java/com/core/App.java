package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * GitHub: （示例代码）
 * https://github.com/josiahcarlson/redis-in-action/tree/master/java/src/main/java
 * 
 * 数据类型:
 * 1. STRING
 * 2. LIST
 * 3. SET
 * 4. HASH
 * 5. ZSET
 *
 */
@SpringBootApplication
public class App {
    public static void main( String[] args ) {
    	SpringApplication.run(App.class, args);
        System.out.println( "Hello World!" );
    }
}