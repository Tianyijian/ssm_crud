package com.tyj.crud.bean;

import javax.validation.constraints.Pattern;

public class Employee {
    private Integer empId;

    @Pattern(regexp = "(^[A-Za-z0-9]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)", message = "�û���������2-5λ���Ļ���6-16λӢ�ĺ��������")
    private String empName;

    private String gender;

    //@Email
    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "�����ʽ����ȷ")
    private String email;

    private Integer dId;
    //ϣ����ѯԱ����ʱ������ϢҲ�ǲ�ѯ�õ�
    private Department department;
    
    
    /**
     * 
     */
    public Employee() {
        super();
    }

    /**
     * @param empId
     * @param empName
     * @param gender
     * @param email
     * @param dId
     */
    public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.gender = gender;
        this.email = email;
        this.dId = dId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }
}