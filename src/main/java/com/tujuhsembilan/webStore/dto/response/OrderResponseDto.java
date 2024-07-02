package com.tujuhsembilan.webStore.dto.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Integer orderId;
    private String ordersCode;
    private Timestamp ordersDate;
    private Integer customerId;
    private Integer itemsId;
    private int quantity; 
    private int totalPrice;
}
