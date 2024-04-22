package com.psi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psi.model.po.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
