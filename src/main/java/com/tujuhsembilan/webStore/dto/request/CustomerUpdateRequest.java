package com.tujuhsembilan.webStore.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateRequest {
    private Integer customerId;
    private String customerName;
    private String customerAddress;
    private String customerCode;
    private String customerPhone;
    private int isActive;
    private MultipartFile pic;
}
