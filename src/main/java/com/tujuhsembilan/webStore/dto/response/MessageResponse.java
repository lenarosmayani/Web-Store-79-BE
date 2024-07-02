package com.tujuhsembilan.webStore.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private String message;
    private int statusCode;
    private String status;

    
}

