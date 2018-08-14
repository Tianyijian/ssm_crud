package com.tyj.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyj.crud.bean.Employee;
import com.tyj.crud.bean.EmployeeExample;
import com.tyj.crud.bean.EmployeeExample.Criteria;
import com.tyj.crud.dao.EmployeeMapper;

/**
 * @author TYJ
 * @since 2018年8月12日 下午10:20:21
 */
@Service
public class EmployeeService {
    
    @Autowired
    EmployeeMapper employeeMapper;
    
    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> getAll() {       
        return employeeMapper.selectByExampleWithDept(null);  
    }

    /**员工保存方法
     * @param employee
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);        
    }

    /**校验员工名字是否已存在
     * @param empName
     * @return true 代表当前姓名可用
     */
    public boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }
}
