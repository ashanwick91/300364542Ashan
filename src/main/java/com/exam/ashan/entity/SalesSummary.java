package com.exam.ashan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SalesSummary {

    private String name;

    private String item;

    private Double amount;
}
