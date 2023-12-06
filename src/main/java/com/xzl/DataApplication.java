package com.xzl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName : DataApplication
 * Package : com.xzl
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 11:39
 * @Version: jdk 1.8
 */
@SpringBootApplication
@EnableCaching
public class DataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }

}
