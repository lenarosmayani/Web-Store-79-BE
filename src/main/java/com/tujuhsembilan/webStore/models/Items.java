package com.tujuhsembilan.webStore.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "items_id")
    private Integer itemsId;

    @Column(name = "items_name", nullable = false)
    private String itemsName;

    @Column(name = "items_code", nullable = false)
    private String itemsCode;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "is_available", nullable = false)
    private int isAvailable;

    @Column(name = "last_re_stock", nullable = false)
    private Timestamp lastRestock;
}