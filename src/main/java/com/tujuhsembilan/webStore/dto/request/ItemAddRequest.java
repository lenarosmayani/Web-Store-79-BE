package com.tujuhsembilan.webStore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemAddRequest {
    private String itemName;
    private String itemCode;
    private int stock;
    private int price;
}
