package com.xzl.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName : DataVo
 * Package : com.xzl.entry
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/5 - 22:26
 * @Version: jdk 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVo {
    private List<Datas> data;
    private int total;
}
