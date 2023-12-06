package com.xzl.common.Http.config;

/**
 * ClassName : RestTemplateConfig
 * Package : com.xzl.common.Http.config
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 18:02
 * @Version: jdk 1.8
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

