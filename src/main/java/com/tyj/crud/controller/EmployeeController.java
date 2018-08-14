package com.tyj.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tyj.crud.bean.Employee;
import com.tyj.crud.bean.Msg;
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
    //@RequestMapping("/emps")
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
    
    /**����Jackson��
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn) {
        //�ⲻ��һ����ҳ��ѯ
        //����pageHelper��ҳ���
        //�ڲ�ѯ֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
        PageHelper.startPage(pn, 5);
        //startPage��������������ѯ����һ����ҳ��ѯ
        List<Employee> emps = employeeService.getAll();
        //ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ�������
        //��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ���������ݣ�����������ʾ��ҳ��
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }
    
    /**����Ա��
     * 1.Ҫ֧��JSR303У�飬��Ҫ����Hibernate-Validator��
     * @param employee
     * @return
     */
    @RequestMapping(value="/emp", method=RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if(result.hasErrors()) {
            //У��ʧ�ܣ�����ʧ�ܣ���ģ̬������ʾУ��ʧ�ܵĴ�����Ϣ
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors =  result.getFieldErrors();
            for(FieldError fieldError:errors) {
              //System.out.println("�����ֶ�����"+fieldError.getField());
              //System.out.println("�����ֶ���Ϣ��"+fieldError.getDefaultMessage());
              map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
          }else {
            employeeService.saveEmp(employee);
            return Msg.success();
          }
    }
    
    /**
     * ���Ա�������Ƿ�
     * @param empName
     * @return
     */
    @RequestMapping(value="/checkuser", method=RequestMethod.POST)
    @ResponseBody
    public Msg checkUser(String empName) {
      //���ж��û����Ƿ��ǺϷ��ģ����Է���ǰ�ˣ�Ҳ���Է��ں�ˣ�
        String regx = "(^[A-Za-z0-9]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if(!empName.matches(regx)) {
          return Msg.fail().add("va_msg", "�û���������2-5λ���Ļ���6-16λӢ�ĺ��������");
        }
       
        //���ݿ��û����ظ�У��
        boolean b = employeeService.checkUser(empName);
        if(b) {
          return Msg.success().add("va_msg", "�û�������");
        }else {
          return Msg.fail().add("va_msg", "�û����ظ�,������");
        }
    }
}
