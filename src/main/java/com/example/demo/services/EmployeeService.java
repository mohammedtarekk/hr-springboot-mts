package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.exceptions.InsufficientVacationBalanceException;
import com.example.demo.exceptions.MissingDataException;
import com.example.demo.repos.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private static final int INITIAL_VAC_BALANCE = 21;

    public List<com.example.demo.entities.Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Long createEmployee(com.example.demo.entities.Employee employee){
        setNewEmpVacationBalance(employee);
        return employeeRepo.save(employee).getId();
    }

    private void setNewEmpVacationBalance(com.example.demo.entities.Employee employee) {
        employee.setVacationBalance(INITIAL_VAC_BALANCE);
    }

    public void updateEmployee(Long id, com.example.demo.entities.Employee employee){
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
        if(id == null) throw new MissingDataException("Employee id is missing");
        if(startDate == null || endDate == null) throw new MissingDataException("Dates are required");
        if(endDate.isBefore(startDate))
            throw new BadDataException("end date should be after start date");
        if(employeeRepo.existsById(id)){
            Integer requestSuccess = employeeRepo.handleEmployeeVacationRequest(id, startDate, endDate);
            if(requestSuccess == 0)
                throw new InsufficientVacationBalanceException("Insufficient vacation balance");
        }
        else
            throw new EmployeeNotFoundException(id);
    }

}
