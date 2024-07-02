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
public class CustomerRequestForm {
    private String customerName;
    private String customerCode;
    private String customerAddress;
    private String customerPhone;
    private MultipartFile pic;
}
