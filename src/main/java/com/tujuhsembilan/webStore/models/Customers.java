package com.tujuhsembilan.webStore.models;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "customers")
public class Customers {

    @Id
    @Column(name = "customer_id", unique = true, nullable= false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "customer_name", nullable= false)
    private String customerName;

    @Column(name = "customer_address", nullable= false)
    private String customerAddress;

    @Column(name = "customer_code", nullable= false)
    private String customerCode;

    @Column(name = "customer_phone", nullable= false)
    private String customerPhone;

    @Column(name = "is_active", nullable= false)
    private int isActive;

    @Column(name = "last_order_date")
    private Timestamp lastOrderDate;

    @Column(name = "pic", nullable= false)
    private String pic;
    
}

