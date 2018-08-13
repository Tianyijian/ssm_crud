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
 * @since 2018��8��13�� ����8:14:41
 * ����Ͳ����йص�����
 */
@Controller
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    /**
     * �������еĲ�����Ϣ
     * @return
     */
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts() {
        List<Department> depts = departmentService.getDepts();
        return Msg.success().add("depts", depts);
    }
}
