package com.tujuhsembilan.webStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.webStore.dto.request.CustomerRequestForm;
import com.tujuhsembilan.webStore.dto.response.CustomerByIdResponse;
import com.tujuhsembilan.webStore.dto.response.CustomerListResponse;
import com.tujuhsembilan.webStore.dto.response.MessageResponse;
import com.tujuhsembilan.webStore.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createCustomer(@ModelAttribute CustomerRequestForm requestForm,
                                                          @RequestParam("pic") MultipartFile pic
    ) {
        return customerService.createCustomer(requestForm, pic);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<MessageResponse> updateCustomer(@PathVariable Integer customerId,
                                                          @ModelAttribute CustomerRequestForm requestForm,
                                                          @RequestParam(value = "pic", required = false) MultipartFile pic) {
        return customerService.updateCustomer(customerId, requestForm, pic);
    }

    @GetMapping("/All")
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerByIdResponse> getCustomerByID(@PathVariable Integer customerId) {
        return customerService.getCustomerByID(customerId);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<MessageResponse> deleteCustomerByID(@PathVariable Integer customerId) {
        return customerService.deleteCustomerByID(customerId);
    }
}
