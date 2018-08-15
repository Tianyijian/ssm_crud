package com.tyj.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @since 2018��8��12�� ����10:09:32 ����Ա��CRUD����
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * @return
     */
    // @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        // �ⲻ��һ����ҳ��ѯ
        // ����pageHelper��ҳ���
        // �ڲ�ѯ֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
        PageHelper.startPage(pn, 5);
        // startPage��������������ѯ����һ����ҳ��ѯ
        List<Employee> emps = employeeService.getAll();
        // ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ�������
        // ��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ���������ݣ�����������ʾ��ҳ��
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }

    /**
     * ����Jackson��
     * 
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        // �ⲻ��һ����ҳ��ѯ
        // ����pageHelper��ҳ���
        // �ڲ�ѯ֮ǰֻ��Ҫ���ã�����ҳ�룬�Լ�ÿҳ�Ĵ�С
        PageHelper.startPage(pn, 5);
        // startPage��������������ѯ����һ����ҳ��ѯ
        List<Employee> emps = employeeService.getAll();
        // ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ�������
        // ��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ���������ݣ�����������ʾ��ҳ��
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * ����Ա�� 1.Ҫ֧��JSR303У�飬��Ҫ����Hibernate-Validator��
     * 
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            // У��ʧ�ܣ�����ʧ�ܣ���ģ̬������ʾУ��ʧ�ܵĴ�����Ϣ
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                // System.out.println("�����ֶ�����"+fieldError.getField());
                // System.out.println("�����ֶ���Ϣ��"+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * ���Ա�������Ƿ�
     * 
     * @param empName
     * @return
     */
    @RequestMapping(value = "/checkuser", method = RequestMethod.POST)
    @ResponseBody
    public Msg checkUser(String empName) {
        // ���ж��û����Ƿ��ǺϷ��ģ����Է���ǰ�ˣ�Ҳ���Է��ں�ˣ�
        String regx = "(^[A-Za-z0-9]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "�û���������2-5λ���Ļ���6-16λӢ�ĺ��������");
        }

        // ���ݿ��û����ظ�У��
        boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success().add("va_msg", "�û�������");
        } else {
            return Msg.fail().add("va_msg", "�û����ظ�,������");
        }
    }

    /**
     * ����id ��ѯԱ��
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    /**
     * ���ֱ�ӷ���ajax=PUT���� 
     * 
     * ��װ�����ݳ���·���ϴ�������ȫ��null
     * 
     * ���⣺ �������������ݣ� ����Employee�����װ����
     * 
     * ԭ�� tomcat: 
     *      1.���������е����ݣ���װ��һ��map,
     *      2.request.getParameter("emName")�ͻ�����map��ȡֵ 
     *      3.SpringMVC��װPOJO�����ʱ��
     * ���POJO��ÿ������ֵ��request.getParamter("email");
     * 
     * AJAX����PUT����������Ѫ�� 
     * 
     * PUT�����������е����ݣ�request.getParamter("email")�ò���
     * Tomcat һ����put����Ͳ����װ�������е�����Ϊmap,ֻ��POST��ʽ������ŷ�װ��map��ʽ
     * 
     * ���������
     * ����Ҫ��֧��ֱ�ӷ���PUT֮�������Ҫ��װ�������е����� 
     * ��web.xml���ù�����HttpPutFormContentFilter���ɽ��
     * �������ã����������е����ݽ�����װ��һ��map
     * request�����°�װ��request.getParameter()����д���ͻ���Լ���װ��map����������
     * 
     * Ա�����·���
     * 
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee) {
        System.out.print("��Ҫ���µ�Ա�����ݣ�"+employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }
    
    /**ɾ������Ա������������һ
     * ������1-2-3
     * ������1
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids")String ids) {
      //����ɾ��
        if(ids.contains("-")) {
          List<Integer> delIds = new ArrayList<Integer>();
          String[] strIds = ids.split("-");
          //��װid�ļ���
          for(String string:strIds) {
            delIds.add(Integer.parseInt(string));
          }
          employeeService.deleteBatch(delIds);
        }else {
          //����ɾ��
          employeeService.deleteEmp(Integer.parseInt(ids));
        }
        
        return Msg.success();
    }
    

}
