package com.tujuhsembilan.webStore.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.webStore.dto.request.OrderRequest;
import com.tujuhsembilan.webStore.dto.response.MessageResponse;
import com.tujuhsembilan.webStore.dto.response.OrderDetailResponse;
import com.tujuhsembilan.webStore.dto.response.OrderListResponse;
import com.tujuhsembilan.webStore.dto.response.OrderResponseDto;
import com.tujuhsembilan.webStore.models.Customers;
import com.tujuhsembilan.webStore.models.Items;
import com.tujuhsembilan.webStore.models.Orders;
import com.tujuhsembilan.webStore.repository.CustomersRepository;
import com.tujuhsembilan.webStore.repository.ItemsRepository;
import com.tujuhsembilan.webStore.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemsRepository itemRepository;

    @Autowired
    private CustomersRepository customerRepository;

    @Autowired
    private MessageSource messageSource;

    public ResponseEntity<MessageResponse> createOrder(OrderRequest request){
        try {
            Orders order = new Orders();
            order.setOrderCode(request.getOrderCode());
            order.setQuantity(request.getQuantity());

            Customers customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> 
            new IllegalArgumentException("Item not found"));
            order.setCustomer(customer);
            

            Items item = itemRepository.findById(request.getItemId()).orElseThrow(() -> 
                new IllegalArgumentException("Item not found"));
            order.setItem(item);

            int totalPrice = item.getPrice() * request.getQuantity();
            order.setTotalPrice(totalPrice);
            
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            order.setOrderDate(timestamp);

            orderRepository.save(order);

            String message = "order created successfully";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> updateOrder(OrderRequest request, Integer orderId) {
      try {
            Optional<Orders> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                String message = "Order not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            Orders order = optionalOrder.get();
            order.setOrderCode(request.getOrderCode());
            order.setQuantity(request.getQuantity());

            Customers customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> 
            new IllegalArgumentException("Item not found"));
            order.setCustomer(customer);
            

            Items item = itemRepository.findById(request.getItemId()).orElseThrow(() -> 
                new IllegalArgumentException("Item not found"));
            order.setItem(item);

            int totalPrice = item.getPrice() * request.getQuantity();
            order.setTotalPrice(totalPrice);
            
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            order.setOrderDate(timestamp);

            orderRepository.save(order);

            String message = "order updated successfully";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

 public ResponseEntity<OrderListResponse> getAllOrder(){
        try {
            List<Orders> orders = orderRepository.findAll();

            List<OrderResponseDto> orderList = orders.stream().map(order ->
                    new OrderResponseDto(
                            order.getOrderId(),
                            order.getOrderCode(),
                            order.getOrderDate(),
							order.getCustomer().getCustomerId(),
							order.getItem().getItemsId(),
                            order.getQuantity(),
                            order.getTotalPrice()
                            
                    )
            ).collect(Collectors.toList());

            long totalData = orders.size();
            log.info("Total orders retrieved: {}", totalData);
            String message = "Get orders success!";

            return ResponseEntity.ok()
                    .body(new OrderListResponse(totalData, orderList, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderListResponse(0, null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<OrderDetailResponse> getOrderByID(Integer orderId) {
        try {
            Optional<Orders> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                String message = "Order not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new OrderDetailResponse(null, message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            Orders order = optionalOrder.get();

            OrderResponseDto orderDetail = new OrderResponseDto(
                order.getOrderId(),
                order.getOrderCode(),
                order.getOrderDate(),
                order.getCustomer().getCustomerId(),
                order.getItem().getItemsId(),
                order.getQuantity(),
                order.getTotalPrice()
            );

            String message = "Get order by ID success";
            return ResponseEntity.ok()
                    .body(new OrderDetailResponse(orderDetail, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new OrderDetailResponse(null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> deleteOrderByID(Integer orderId) {
        try {
            Optional<Orders> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                String message = "Order not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            orderRepository.deleteById(orderId);

            String message = "Order deleted successfully";
            return ResponseEntity.ok()
                    .body(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }
}
