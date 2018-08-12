package com.tyj.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tyj.crud.bean.Department;
import com.tyj.crud.bean.Employee;
import com.tyj.crud.dao.DepartmentMapper;
import com.tyj.crud.dao.EmployeeMapper;

/**
 * @author TYJ
 * @since 2018��8��12�� ����3:27:47
 * ����dao��Ĺ���
 * �Ƽ�Spring����Ŀ�Ϳ���ʹ��Spring�ĵ�Ԫ���ԣ������Զ�ע��������Ҫ�����
 * 1.����SpringTestģ��
 * 2.@ContextConfigurationָ��spring�����ļ���λ��
 * 3.ֱ��autowiredҪʹ�õ��������
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;
    
    @Autowired
    EmployeeMapper employeeMapper;
    
    @Autowired
    SqlSession sqlSession;
    /**
     * ����DepartmentMapper
     */
    @Test
    public void testCRUD() {
//        //1.����Spring IOC����
//        ApplicationContext ioc = new ClassPathXmlApplicationContext();
//        //2.�������л�ȡmapper
//        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
        
        System.out.println(departmentMapper);
        
        //1.���뼸������
//        departmentMapper.insertSelective(new Department(null, "������"));
//        departmentMapper.insertSelective(new Department(null, "���Բ�"));
        
        //2.����Ա�����ݣ�����Ա������
//        employeeMapper.insertSelective(new Employee(null, "tyj", "M", "Tyj@tyj.com", 1));
     
        //3.����������Ա����������ʹ�ÿ���ִ������������sqlSession
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null, uid, "M", uid + "@tyj.com", 1));
        }
        System.out.println("�����������");
    }
}
