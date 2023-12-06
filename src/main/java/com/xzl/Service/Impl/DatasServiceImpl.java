package com.xzl.Service.Impl;

import com.xzl.Service.DatasService;
import com.xzl.dao.DataDAO;
import com.xzl.entry.Datas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName : DatasServiceImpl
 * Package : com.xzl.Service.Impl
 * Description :
 *
 * @Author : 欧显多
 * @Create : 2023/12/6 - 9:38
 * @Version: jdk 1.8
 */
@Service
public class DatasServiceImpl implements DatasService {
    @Autowired
    private DataDAO dataDAO;
    @Override
    public void saveAll(List<Datas> data) {
        dataDAO.saveAll(data);
    }
}
