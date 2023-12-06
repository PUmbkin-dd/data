package com.xzl.common.Http;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import org.springframework.context.annotation.Configuration;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * ClassName : httpcommon
 * Package : com.xzl.common.Http
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 10:40
 * @Version: jdk 1.8
 */
public class httpCommon {

    private static final String LOGIN_URL = "https://https://emt.jinqucloud.com/login";
    private static final String DATA_URL = "https://https://emt.jinqucloud.com/index";

    private String loginAndGetCookie(String username, String password) throws IOException {
        // 构建登录请求参数
        String requestBody = "username=" + username + "&password=" + password;

        // 创建登录URL对象
        URL loginUrl = new URL(LOGIN_URL);
        HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();

        // 设置请求方法为POST
        connection.setRequestMethod("POST");

        // 允许输入输出流
        connection.setDoOutput(true);

        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // 获取输出流并写入请求体
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 获取Cookie
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> cookies = headerFields.get("Set-Cookie");

        // 提取Cookie信息
        if (cookies != null) {
            return String.join("; ", cookies);
        }

        return null;
    }

    private String fetchDataPage(String cookie) throws IOException {
        // 创建获取数据的请求
        URL dataUrl = new URL(DATA_URL);
        HttpURLConnection connection = (HttpURLConnection) dataUrl.openConnection();

        // 设置Cookie头
        connection.setRequestProperty("Cookie", cookie);

        // 获取数据
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        }
    }
}
