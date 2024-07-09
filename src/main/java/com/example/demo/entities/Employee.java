package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@NamedStoredProcedureQuery(name = "Employee.handleVacationRequest",
        procedureName = "HR.HANDLE_EMPLOYEE_VACATION_REQ",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_EMP_ID", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_START_DATE", type = LocalDate.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "P_END_DATE", type = LocalDate.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "OUT_IS_SUCCESS", type = Integer.class)
        })
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ")
    @SequenceGenerator(name = "EMP_SEQ", sequenceName = "HR.EMP_SEQ", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID")
    private Department department;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate hireDate;
    private String phoneNumber;
    private Float salary;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer vacationBalance;

    public Employee(){}

    public Employee(Long id, Department department, String email, String firstName, String lastName, LocalDate hireDate, String phoneNumber, Float salary, Integer vacationBalance) {
        this.id = id;
        this.department = department;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.vacationBalance = vacationBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Integer getVacationBalance() {
        return vacationBalance;
    }

    public void setVacationBalance(Integer vacationBalance) {
        this.vacationBalance = vacationBalance;
    }
}
