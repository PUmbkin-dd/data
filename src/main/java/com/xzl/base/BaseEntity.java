package com.xzl.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * ClassName : BaseEntity
 * Package : com.xzl.base
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/6 - 10:07
 * @Version: jdk 1.8
 */
@Data
@MappedSuperclass
@ApiModel(value = "基础实体类")
public class BaseEntity {
    @Id
    @GenericGenerator(name = "systemUUID", strategy = "uuid")
    @GeneratedValue(generator = "systemUUID")
    private String id;
}
