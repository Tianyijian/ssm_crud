package com.tyj.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyj.crud.bean.Employee;
import com.tyj.crud.dao.EmployeeMapper;

/**
 * @author TYJ
 * @since 2018��8��12�� ����10:20:21
 */
@Service
public class EmployeeService {
    
    @Autowired
    EmployeeMapper employeeMapper;
    
    /**
     * ��ѯ����Ա��
     * @return
     */
    public List<Employee> getAll() {       
        return employeeMapper.selectByExampleWithDept(null);  
    }

    /**Ա�����淽��
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);        
    }
}
