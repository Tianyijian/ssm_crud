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
 * @since 2018年8月12日 下午10:09:32 处理员工CRUD请求
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
        // 这不是一个分页查询
        // 引入pageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        // 封装了详细的分页信息，包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }

    /**
     * 导入Jackson包
     * 
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        // 这不是一个分页查询
        // 引入pageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        // 封装了详细的分页信息，包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 保存员工 1.要支持JSR303校验，需要导入Hibernate-Validator包
     * 
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            // 校验失败，返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                // System.out.println("错误字段名："+fieldError.getField());
                // System.out.println("错误字段信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * 检查员工名字是否
     * 
     * @param empName
     * @return
     */
    @RequestMapping(value = "/checkuser", method = RequestMethod.POST)
    @ResponseBody
    public Msg checkUser(String empName) {
        // 先判断用户名是否是合法的（可以放在前端，也可以放在后端）
        String regx = "(^[A-Za-z0-9]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是2-5位中文或者6-16位英文和数字组合");
        }

        // 数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success().add("va_msg", "用户名可用");
        } else {
            return Msg.fail().add("va_msg", "用户名重复,不可用");
        }
    }

    /**
     * 根据id 查询员工
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
     * 如果直接发送ajax=PUT请求 
     * 
     * 封装的数据除了路径上带的数据全是null
     * 
     * 问题： 请求体中有数据， 但是Employee对象封装不上
     * 
     * 原因： tomcat: 
     *      1.将请求体中的数据，封装成一个map,
     *      2.request.getParameter("emName")就会从这个map中取值 
     *      3.SpringMVC封装POJO对象的时候，
     * 会把POJO中每个属性值，request.getParamter("email");
     * 
     * AJAX发送PUT请求引发的血案 
     * 
     * PUT请求，请求体中的数据，request.getParamter("email")拿不到
     * Tomcat 一看是put请求就不会封装请求体中的数据为map,只有POST形式的请求才封装成map形式
     * 
     * 解决方案：
     * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据 
     * 在web.xml配置过滤器HttpPutFormContentFilter即可解决
     * 它的作用：将请求体中的数据解析包装成一个map
     * request被重新包装，request.getParameter()被重写，就会从自己封装的map中请求数据
     * 
     * 员工更新方法
     * 
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee) {
        System.out.print("将要更新的员工数据："+employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }
    
    /**删除单个员工与批量二合一
     * 批量：1-2-3
     * 单个：1
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids")String ids) {
      //批量删除
        if(ids.contains("-")) {
          List<Integer> delIds = new ArrayList<Integer>();
          String[] strIds = ids.split("-");
          //组装id的集合
          for(String string:strIds) {
            delIds.add(Integer.parseInt(string));
          }
          employeeService.deleteBatch(delIds);
        }else {
          //单个删除
          employeeService.deleteEmp(Integer.parseInt(ids));
        }
        
        return Msg.success();
    }
    

}
