import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.net.*;
/**
 * ClassName : Text
 * Package : PACKAGE_NAME
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 13:17
 * @Version: jdk 1.8
 */
public class Text {
    private static final String LOGIN_URL = "https://emt.jinqucloud.com/login";
    private static final String DATA_URL = "https://emt.jinqucloud.com/index";
    private static final String IS_WINDOW = "1";
    private static final String REMEMBER_ME = "false";
    private String loginAndGetCookie() throws IOException {
        // 构建登录请求参数
        String requestBody = "username=" + URLEncoder.encode("hlccx01", String.valueOf(StandardCharsets.UTF_8))
                + "&password=" + URLEncoder.encode("456123", String.valueOf(StandardCharsets.UTF_8))
                + "&isWindow=" + URLEncoder.encode(IS_WINDOW, String.valueOf(StandardCharsets.UTF_8))
                + "&rememberMe=" + URLEncoder.encode(REMEMBER_ME, String.valueOf(StandardCharsets.UTF_8));
        // 创建登录URL对象
        URL loginUrl = new URL(LOGIN_URL);
        HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();

        // 设置请求方法为POST
        connection.setRequestMethod("POST");

        // 允许输入输出流
        connection.setDoOutput(true);

        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/json");

        // 获取输出流并写入请求体
        try (OutputStream os = connection.getOutputStream()) {

            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            System.out.println(Arrays.toString(input));
            os.write(input, 0, input.length);
        }

        // 获取状态码
        int responseCode = connection.getResponseCode();
        String responseBody = getResponseBody(connection);
        System.out.println("Response Body: " + responseBody);
        System.out.println(connection);
        // 判断是否登录成功
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 登录成功，获取Cookie
            Map<String, List<String>> headerFields = connection.getHeaderFields();

            // 输出整个请求头
            for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            List<String> cookies = headerFields.get("Cookie");

            // 提取Cookie信息
            if (cookies != null) {
                // 设置Cookie到后续请求的请求头中
                connection.setRequestProperty("Cookie", String.join("; ", cookies));
                return String.join("; ", cookies);
            }
        }

        return null;
    }


    private String getResponseBody(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            return response.toString();
        }
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
        @Test
        public void list() throws IOException {
            String cookie = loginAndGetCookie();
            String s = fetchDataPage(cookie);
            System.out.println(s);
        }
}
