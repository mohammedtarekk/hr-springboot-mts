package com.example.demo.DTOs;

import lombok.Data;

@Data
public class AnnualRaiseRequest {
    private Float raisePercentage;
    private Integer minHiringMonths;
}
