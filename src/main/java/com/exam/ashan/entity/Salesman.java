package com.exam.ashan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity(name = "salesman")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Salesman {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "dot")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dot;

    @Column(name = "item")
    private String item;

    @Column(name = "name")
    private String name;

}
