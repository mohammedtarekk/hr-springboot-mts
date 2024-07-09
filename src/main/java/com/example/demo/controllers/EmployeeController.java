package com.example.demo.controllers;

import com.example.demo.DTOs.AnnualRaiseRequest;
import com.example.demo.DTOs.EmployeeVacationRequest;
import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/vacations")
    public ResponseEntity<?> handleEmployeeVacationRequest(@PathVariable Long id, @RequestBody EmployeeVacationRequest request) {
        employeeService.handleEmployeeVacationRequest(id, request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok().build();
    }

    @PostMapping("salary")
    public ResponseEntity<?> handleEmployeesAnnualRaise(@RequestBody AnnualRaiseRequest request) {
        employeeService.handleAnnualRaise(request.getRaisePercentage(), request.getMinHiringMonths());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/vacations/v2")
    public ResponseEntity<?> handleEmployeeVacationRequestV2(@PathVariable Long id, @RequestBody EmployeeVacationRequest request) {
        employeeService.handleEmployeeVacationRequestV2(id, request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok().build();
    }

}