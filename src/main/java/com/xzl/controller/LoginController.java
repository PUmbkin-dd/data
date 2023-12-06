package com.xzl.controller;

/**
 * ClassName : LoginController
 * Package : com.xzl.controller
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 17:26
 * @Version: jdk 1.8
 */
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzl.Service.DatasService;
import com.xzl.dao.DataDAO;
import com.xzl.entry.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DatasService datasService;

    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "30";

    @PostMapping("/askCookie")
    public String login(@ModelAttribute LoginRequest loginForm) {
        // 定义目标登录URL
        String loginUrl = "https://emt.jinqucloud.com/login";

        // 构建表单数据
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", loginForm.getUsername());
        formData.add("password", loginForm.getPassword());
        formData.add("isWindow", loginForm.getIsWindow());
        formData.add("rememberMe", loginForm.getRememberMe());

        // 创建请求头并设置 Content-Type 为 application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 构建请求体
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
//        System.out.println(requestEntity);
        // 发送POST请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);
//        System.out.println("o_O = =  =  " + responseEntity);;
        // 检查响应，根据需要进行处理
        int statusCode = responseEntity.getStatusCodeValue();
        String cookie = null;
        // 检查是否包含Set-Cookie头，如果有，则提取并处理Cookie
        List<String> setCookieHeaders = responseEntity.getHeaders().get("Set-Cookie");
        if (setCookieHeaders != null) {
            for (String setCookieHeader : setCookieHeaders) {
                cookie = extractJSessionId(setCookieHeader);
//                System.out.println(" 11111111111 o_O" + cookie);

            }
        }

        if (statusCode == 200) {
            return cookie;
        } else {
            return ("G!");
        }
    }

    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // 处理异常，返回原始字符串或其他默认值
            return value;
        }
    }

    @PostMapping("/list")
    public DataVo list(@ModelAttribute ListRequest listForm) throws IOException {
        String listUrl = "https://emt.jinqucloud.com/system/dynamic/list?id=2743";

        // 构建表单数据
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("pageSize", listForm.getRows());
        formData.add("pageNum", listForm.getFist());
        formData.add("out_weight_time", listForm.getStartDate());
        formData.add("out_weight_time_end_phone", listForm.getEndDate());
        formData.add("orderByColumn", "create_time");
        formData.add("isAsc", "desc");
        formData.add("report_one_load", "1");
        formData.add("quickQuery=bigSearch", "");
        // 创建请求头并设置 Content-Type 为 application/x-www-form-urlencoded
//        System.out.println(formData);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(HttpHeaders.COOKIE, listForm.getCookie());
        // 构建请求体
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
//        System.out.println(requestEntity);
        // 发送POST请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(listUrl, HttpMethod.POST, requestEntity, String.class);
//        System.out.println("o_O = =  =  " + responseEntity);
        // 检查响应，根据需要进行处理
//        System.out.println(formData);
        int statusCode = responseEntity.getStatusCodeValue();
//        System.out.println(statusCode);
        String body = responseEntity.getBody();
//        System.out.println(body);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(body);
        JsonNode rowsNode = jsonNode.get("rows");
        JsonNode total = jsonNode.get("total");
        // 判断 "rows" 是否存在，以及其值是否为数组节点
        List<Datas> list = objectMapper.readValue(rowsNode.traverse(), new TypeReference<List<Datas>>() {});
        // 现在你可以使用 list，它包含了 "rows" 的值对应的 List<Datas> 对象
//        System.out.println(list);
        Integer totalNumber = objectMapper.readValue(total.traverse(), int.class);

        return new DataVo(list, totalNumber);
    }


    @PostMapping("/caret")
    public DataVo caret(@RequestBody Model model) {
        String cookie = null;

        try {
            cookie =  login(new LoginRequest(model.getUsername(), model.getPassword(), "1", "false"));

            int pageSize = 30;
            int currentPage = 1;

            List<Datas> allData = new ArrayList<>();

            while (true) {
                try {
                    DataVo currentPageData =list(new ListRequest(cookie, model.getStartData(), model.getEndData(), String.valueOf(currentPage), String.valueOf(pageSize)));
                    List<Datas> data = currentPageData.getData();
                    try {
                        datasService.saveAll(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("--------------存入失败-------------------");
                        System.out.println(currentPage+"异常");
                    }
                    System.out.println(data);
                    System.out.println("--------------存入成功-------------------");
                    allData.addAll(data);

                    if (currentPageData.getData().size() < pageSize) {
                        // Break if the last page is reached
                        System.out.println("数据完成");
                        break;
                    }

                    currentPage++;
                } catch (Exception e) {
                    // Handle Cookie expiration by re-logging in
                    cookie = login(new LoginRequest(model.getUsername(), model.getPassword(), "1", "false"));
                }
            }

            return new DataVo(allData, allData.size());
        } catch (Exception e) {
            // Handle other exceptions or log errors
            e.printStackTrace();
        }

        return null;



    }
    public static String formatToJson(String input) {
        // 使用正则表达式在属性名周围添加双引号
        String pattern = "(\\w+)=([^,]+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, "\"" + matcher.group(1) + "\":\"" + matcher.group(2) + "\"");
        }
        matcher.appendTail(result);

        // 添加大括号，使其成为标准的 JSON 格式
        return  result.toString();
    }


    private String extractJSessionId(String fullCookie) {
        if (fullCookie != null && fullCookie.contains("JSESSIONID")) {
            int startIndex = fullCookie.indexOf("JSESSIONID");
            int endIndex = fullCookie.indexOf(";", startIndex);
            endIndex = (endIndex > 0) ? endIndex : fullCookie.length();
            return fullCookie.substring(startIndex, endIndex);
        }
        return null;
    }
}

