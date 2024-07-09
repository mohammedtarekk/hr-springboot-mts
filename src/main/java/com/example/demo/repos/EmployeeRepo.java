package com.example.demo.repos;

import com.example.demo.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Modifying
    @Query("DELETE FROM #{#entityName} e WHERE e.id = :id")
    void deleteById(@Param("id") Long id);

    @Procedure(name = "Employee.handleVacationRequest")
    @Transactional
    Integer handleEmployeeVacationRequest(@Param("P_EMP_ID") Long id, @Param("P_START_DATE") LocalDate startDate, @Param("P_END_DATE") LocalDate endDate);

    @Procedure(name = "Employee.handleAnnualRaise")
    @Transactional
    void handleEmployeesAnnualRaise(@Param("P_RAISE_PRECENTAGE") Float raisePercentage, @Param("P_MIN_HIRING_MONTHS") Integer minHiringMonths);

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.vacationBalance = e.vacationBalance - :requestedDaysCount WHERE e.id = :id")
    void updateEmpVacationBalance(Long id, long requestedDaysCount);

    @Modifying
    @Query(value = "INSERT INTO EMPLOYEES_VACATION_HISTORY (VACATION_START_DATE, VACATION_END_DATE, EMPLOYEE_ID)\n" +
            " VALUES (:startDate , :endDate , :id)", nativeQuery = true)
    void insertEmpVacationHistory(Long id, LocalDate startDate, LocalDate endDate);
}
