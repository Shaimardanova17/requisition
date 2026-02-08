package com.example.demo.repo;

import com.example.demo.entity.RequisitionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequisitionItemRepository extends JpaRepository<RequisitionItem, Long> {

}
