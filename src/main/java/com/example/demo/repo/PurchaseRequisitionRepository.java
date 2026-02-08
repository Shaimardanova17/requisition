package com.example.demo.repo;

import com.example.demo.entity.PurchaseRequisition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Long> {
}
