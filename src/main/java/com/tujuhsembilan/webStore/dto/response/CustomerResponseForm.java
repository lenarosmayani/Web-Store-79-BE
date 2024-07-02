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
public class CustomerResponseForm {
    private Integer customerId;
    private String customerName;
    private String customerCode;
    private String customerAddress;
    private String customerPhone;
    private String pic;
    private int isActive;
    private Timestamp LastOrderDate;
}
