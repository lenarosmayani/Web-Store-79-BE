package com.tujuhsembilan.webStore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerByIdResponse {
    private CustomerResponseForm data;
    private String message;
    private int statusCode;
    private String status;
    
}
