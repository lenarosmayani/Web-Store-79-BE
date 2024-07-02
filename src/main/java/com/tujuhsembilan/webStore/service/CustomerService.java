package com.tujuhsembilan.webStore.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tujuhsembilan.webStore.dto.request.CustomerRequestForm;
import com.tujuhsembilan.webStore.dto.response.CustomerByIdResponse;
import com.tujuhsembilan.webStore.dto.response.CustomerListResponse;
import com.tujuhsembilan.webStore.dto.response.CustomerResponseForm;
import com.tujuhsembilan.webStore.dto.response.MessageResponse;
import com.tujuhsembilan.webStore.models.Customers;
import com.tujuhsembilan.webStore.repository.CustomersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomersRepository customerRepo;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MinIoService minIoService;

    Logger logger = LoggerFactory.getLogger(getClass());
    
    public ResponseEntity<MessageResponse> createCustomer(CustomerRequestForm request, MultipartFile picFile) {
        try {
            Customers customer = new Customers();
            customer.setCustomerName(request.getCustomerName());
            customer.setCustomerCode(request.getCustomerCode());
            customer.setCustomerAddress(request.getCustomerAddress());
            customer.setCustomerPhone(request.getCustomerPhone());
            customer.setIsActive(1);

            if (picFile != null && !picFile.isEmpty()) {
                String pic = minIoService.uploadFile(picFile);
                customer.setPic(pic);
            } else {
                log.warn("No picture file provided for customer: {}", request.getCustomerName());
            }

            customerRepo.save(customer);
            log.info("Customer created: {}", customer);

            String message = "Customer created successfully!";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> updateCustomer(Integer customerId, CustomerRequestForm request, MultipartFile picFile) {
        try {
            Optional<Customers> optionalCustomer = customerRepo.findById(customerId);
            if (!optionalCustomer.isPresent()) {
                String message = "Customer not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            Customers customer = optionalCustomer.get();
            customer.setCustomerName(request.getCustomerName());
            customer.setCustomerCode(request.getCustomerCode());
            customer.setCustomerAddress(request.getCustomerAddress());
            customer.setCustomerPhone(request.getCustomerPhone());

            if (picFile != null && !picFile.isEmpty()) {
                String pic = minIoService.uploadFile(picFile);
                customer.setPic(pic);
            }

            customerRepo.save(customer);
            log.info("Customer updated: {}", customer);

            String message = "Customer updated successfully!";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        try {
            List<Customers> customers = customerRepo.findAll();

            List<CustomerResponseForm> custList = customers.stream().map(customer ->
                    new CustomerResponseForm(
                            customer.getCustomerId(),
                            customer.getCustomerName(),
                            customer.getCustomerCode(),
                            customer.getCustomerAddress(),
                            customer.getCustomerPhone(),
                            customer.getPic(),
                            customer.getIsActive(),
                            customer.getLastOrderDate()
                    )
            ).collect(Collectors.toList());

            long totalData = customers.size();
            log.info("Total customers retrieved: {}", totalData);
            String message = "Get customers success!";

            return ResponseEntity.ok()
                    .body(new CustomerListResponse(totalData, custList, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomerListResponse(0, null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<CustomerByIdResponse> getCustomerByID(Integer customerId) {
        try {
            Optional<Customers> optionalCustomer = customerRepo.findById(customerId);
            if (!optionalCustomer.isPresent()) {
                String message = "Customer not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomerByIdResponse(null, message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            Customers customer = optionalCustomer.get();

            CustomerResponseForm customerData = new CustomerResponseForm(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerCode(),
                    customer.getCustomerAddress(),
                    customer.getCustomerPhone(),
                    customer.getPic(),
                    customer.getIsActive(),
                    customer.getLastOrderDate()
            );

            log.info("Customer retrieved by ID {}: {}", customerId, customerData);
            String message = "Get customer by ID success";

            return ResponseEntity.ok()
                    .body(new CustomerByIdResponse(customerData, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = "Internal server error";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomerByIdResponse(null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> deleteCustomerByID(Integer customerId) {
        try {
            if (!customerRepo.existsById(customerId)) {
                String message = "Customer not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            customerRepo.deleteById(customerId);

            String message = "Customer deleted successfully";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = "Internal server error";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }
}

