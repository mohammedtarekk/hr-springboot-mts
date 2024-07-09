package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.exceptions.InsufficientVacationBalanceException;
import com.example.demo.exceptions.MissingDataException;
import com.example.demo.repos.EmployeeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private static final int INITIAL_VAC_BALANCE = 21;

    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Long createEmployee(Employee employee){
        setNewEmpVacationBalance(employee);
        return employeeRepo.save(employee).getId();
    }

    private void setNewEmpVacationBalance(Employee employee) {
        employee.setVacationBalance(INITIAL_VAC_BALANCE);
    }

    public void updateEmployee(Long id, Employee employee){
        if(id == null) throw new MissingDataException("Employee id is missing");
        if(!id.equals(employee.getId())) throw new BadDataException("Employee id should match with the given resource id");
        if(employeeRepo.existsById(id)){
            employeeRepo.save(employee);
        }
        else
            throw new EmployeeNotFoundException(id);
    }

    public void deleteEmployee(Long id){
        if(id == null) throw new MissingDataException("Employee id is missing");
        employeeRepo.deleteById(id);
    }

    public void handleEmployeeVacationRequest(Long id, LocalDate startDate, LocalDate endDate){
       validateVacationRequest(id, startDate, endDate);
        if(employeeRepo.existsById(id)){
            Integer requestSuccess = employeeRepo.handleEmployeeVacationRequest(id, startDate, endDate);
            if(requestSuccess == 0)
                throw new InsufficientVacationBalanceException("Insufficient vacation balance");
        }
        else
            throw new EmployeeNotFoundException(id);
    }

    @Transactional
    public void handleEmployeeVacationRequestV2(Long id, LocalDate startDate, LocalDate endDate) {
        validateVacationRequest(id, startDate, endDate);
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        long requestedDaysCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if(requestedDaysCount <= employee.getVacationBalance()){
            employeeRepo.updateEmpVacationBalance(id, requestedDaysCount);
            employeeRepo.insertEmpVacationHistory(id, startDate, endDate);
        }
        else
            throw new InsufficientVacationBalanceException("Insufficient vacation balance");
    }
    
    private void validateVacationRequest(Long id, LocalDate startDate, LocalDate endDate){
        if(id == null)
            throw new MissingDataException("Employee id is missing");
        if(startDate == null || endDate == null)
            throw new MissingDataException("Dates are required");
        if(endDate.isBefore(startDate))
            throw new BadDataException("end date should be after start date");
    }

    public void handleAnnualRaise(Float raisePercentage, Integer minHiringMonths) {
        if(raisePercentage == null || minHiringMonths == null)
            throw new MissingDataException("Missing required data.");
        employeeRepo.handleEmployeesAnnualRaise(raisePercentage, minHiringMonths);
    }

}
