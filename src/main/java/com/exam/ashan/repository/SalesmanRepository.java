package com.exam.ashan.repository;

import com.exam.ashan.entity.SalesSummary;
import com.exam.ashan.entity.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {

    @Query("SELECT new com.exam.ashan.entity.SalesSummary(s.name, s.item, sum(s.amount)) " +
            "FROM salesman s " +
            "GROUP BY s.name, s.item")
    List<SalesSummary> findSalesSummary();
}
