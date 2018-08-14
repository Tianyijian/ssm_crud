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
 * @since 2018年8月12日 下午10:09:32
 * 处理员工CRUD请求
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
        //这不是一个分页查询
        //引入pageHelper分页插件
        //在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        model.addAttribute("pageInfo", page);
        return "list";
    }
    
    /**导入Jackson包
     * @param pn
     * @return
     */
    @ResponseBody
    @RequestMapping("/emps")
    public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn) {
        //这不是一个分页查询
        //引入pageHelper分页插件
        //在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
        //封装了详细的分页信息，包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }
    
    /**保存员工
     * 1.要支持JSR303校验，需要导入Hibernate-Validator包
     * @param employee
     * @return
     */
    @RequestMapping(value="/emp", method=RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if(result.hasErrors()) {
            //校验失败，返回失败，在模态框中显示校验失败的错误信息
            Map<String, Object> map = new HashMap<String, Object>();
            List<FieldError> errors =  result.getFieldErrors();
            for(FieldError fieldError:errors) {
              //System.out.println("错误字段名："+fieldError.getField());
              //System.out.println("错误字段信息："+fieldError.getDefaultMessage());
              map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
          }else {
            employeeService.saveEmp(employee);
            return Msg.success();
          }
    }
    
    /**
     * 检查员工名字是否
     * @param empName
     * @return
     */
    @RequestMapping(value="/checkuser", method=RequestMethod.POST)
    @ResponseBody
    public Msg checkUser(String empName) {
      //先判断用户名是否是合法的（可以放在前端，也可以放在后端）
        String regx = "(^[A-Za-z0-9]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if(!empName.matches(regx)) {
          return Msg.fail().add("va_msg", "用户名必须是2-5位中文或者6-16位英文和数字组合");
        }
       
        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if(b) {
          return Msg.success().add("va_msg", "用户名可用");
        }else {
          return Msg.fail().add("va_msg", "用户名重复,不可用");
        }
    }
}
