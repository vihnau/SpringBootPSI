package com.psi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psi.model.po.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
