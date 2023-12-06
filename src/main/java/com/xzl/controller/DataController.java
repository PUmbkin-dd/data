package com.xzl.controller;

import com.xzl.entry.Datas;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName : DataContcroller
 * Package : com.xzl.controller
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/1 - 13:04
 * @Version: jdk 1.8
 */
@RestController
@RequestMapping(value = "/system")
public class DataController {
    @GetMapping("list")
    public List<Datas> list(String name, String password) {return null;}
}
