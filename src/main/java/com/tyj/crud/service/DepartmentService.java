package com.tyj.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyj.crud.bean.Department;
import com.tyj.crud.dao.DepartmentMapper;

/**
 * @author TYJ
 * @since 2018年8月13日 下午8:15:37
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * @return
     */
    public List<Department> getDepts() {
        List<Department> list = departmentMapper.selectByExample(null);
        return list;
    }

}
