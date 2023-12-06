package com.xzl.entry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName : LoginRequest
 * Package : com.xzl.entry
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 17:31
 * @Version: jdk 1.8
 */


@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
    private String isWindow = "1";
    private String rememberMe ="false";

    // 省略构造函数、getter 和 setter 方法
}

