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

    @Procedure(name = "Employee.handleVacationRequestg")
    @Transactional
    Integer handleEmployeeVacationRequest(@Param("P_EMP_ID") Long id, @Param("P_START_DATE") LocalDate startDate, @Param("P_END_DATE") LocalDate endDate);
}
