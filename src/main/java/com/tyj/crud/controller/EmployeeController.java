package com.tyj.crud.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyj.crud.bean.Employee;
import com.tyj.crud.service.EmployeeService;

/**
 * @author TYJ
 * @since 2018��8��12�� ����10:09:32
 * ����Ա��CRUD����
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    /**
     * @return
     */
    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model) {
        //�ⲻ��һ����ҳ��ѯ
        //����pageHelper��ҳ���
        //�ڲ�ѯ֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
        PageHelper.startPage(pn, 5);
        //startPage��������������ѯ����һ����ҳ��ѯ
        List<Employee> emps = employeeService.getAll();
        //ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ�������
        //��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ���������ݣ�����������ʾ��ҳ��
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }
}
