package com.tujuhsembilan.webStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.webStore.models.Items;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {

}
