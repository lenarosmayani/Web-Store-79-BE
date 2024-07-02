package com.tujuhsembilan.webStore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.webStore.models.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {

    Optional<Customers> findByCustomerIdAndIsActive(int id, int isActive);
    List<Customers> findByIsActiveOrderByCustomerIdAsc(int isActive);
}
