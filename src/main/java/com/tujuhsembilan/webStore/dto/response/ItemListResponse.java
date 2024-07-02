package com.tujuhsembilan.webStore.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemListResponse {
    private long totalData;
    private List<ItemDetailResponse> dataItems;
    private String message;
    private int statusCode;
    private String status;
    
}
