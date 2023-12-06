package com.xzl.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ClassName : ListRequest
 * Package : com.xzl.entry
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/4 - 9:20
 * @Version: jdk 1.8
 */
@Data
@AllArgsConstructor
public class ListRequest {

    private String cookie;
    private String startDate;
    private String endDate;
    private String fist;
    private String rows;

}
