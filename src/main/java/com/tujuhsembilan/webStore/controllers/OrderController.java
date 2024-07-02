package com.tujuhsembilan.webStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.webStore.dto.request.OrderRequest;
import com.tujuhsembilan.webStore.dto.response.MessageResponse;
import com.tujuhsembilan.webStore.dto.response.OrderDetailResponse;
import com.tujuhsembilan.webStore.dto.response.OrderListResponse;
import com.tujuhsembilan.webStore.service.OrderService;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<MessageResponse> updateOrder(@RequestBody OrderRequest request, @PathVariable Integer orderId) {
        return orderService.updateOrder(request, orderId);
    }

    @GetMapping("/all")
    public ResponseEntity<OrderListResponse> getAllOrders() {
        return orderService.getAllOrder();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderByID(orderId);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<MessageResponse> deleteOrderById(@PathVariable Integer orderId) {
        return orderService.deleteOrderByID(orderId);
    }
}
