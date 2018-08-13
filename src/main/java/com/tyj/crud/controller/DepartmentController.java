package com.tyj.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tyj.crud.bean.Department;
import com.tyj.crud.bean.Msg;
import com.tyj.crud.service.DepartmentService;

/**
 * @author TYJ
 * @since 2018年8月13日 下午8:14:41
 * 处理和部门有关的请求
 */
@Controller
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    /**
     * 返回所有的部门信息
     * @return
     */
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts() {
        List<Department> depts = departmentService.getDepts();
        return Msg.success().add("depts", depts);
    }
}
