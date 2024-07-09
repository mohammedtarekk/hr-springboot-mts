package com.example.demo.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeVacationRequest {

    private LocalDate startDate;
    private LocalDate endDate;

}
