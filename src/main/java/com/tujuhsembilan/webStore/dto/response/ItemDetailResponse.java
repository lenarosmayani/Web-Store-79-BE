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
public class ItemDetailResponse {
    private Integer itemId;
    private String itemName;
    private String itemCode;
    private int itemStock;
    private int price;
    private int isAvailable;
    private Timestamp lastRestock;
}
