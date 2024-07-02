package com.tujuhsembilan.webStore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private OrderResponseDto data;
    private String message;
    private int statusCode;
    private String status;
}
