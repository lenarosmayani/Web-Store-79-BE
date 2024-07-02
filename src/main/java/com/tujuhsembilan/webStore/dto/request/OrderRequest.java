package com.tujuhsembilan.webStore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String orderCode;
    private Integer customerId;
    private Integer itemId;
    private int quantity;
}
