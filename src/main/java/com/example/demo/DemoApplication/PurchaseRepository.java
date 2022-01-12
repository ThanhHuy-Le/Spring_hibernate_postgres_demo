package com.example.demo.DemoApplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByCustomerId(Long customer_id);

}