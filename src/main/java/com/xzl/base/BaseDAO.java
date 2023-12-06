package com.xzl.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author leihfei
 * 基础dao接口
 * @email:leihfein@gmail.com
 * @since 10:30 2018/12/9
 */
@NoRepositoryBean
public interface BaseDAO<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

}
