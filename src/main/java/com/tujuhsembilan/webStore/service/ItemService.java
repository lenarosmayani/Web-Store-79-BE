package com.tujuhsembilan.webStore.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import com.tujuhsembilan.webStore.dto.request.ItemAddRequest;
import com.tujuhsembilan.webStore.dto.request.ItemUpdateRequest;
import com.tujuhsembilan.webStore.dto.response.ItemDetailResponse;
import com.tujuhsembilan.webStore.dto.response.ItemDetailResponseDto;
import com.tujuhsembilan.webStore.dto.response.ItemListResponse;
import com.tujuhsembilan.webStore.dto.response.MessageResponse;
import com.tujuhsembilan.webStore.models.Items;
import com.tujuhsembilan.webStore.repository.ItemsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private MessageSource messageSource;

    Logger logger = LoggerFactory.getLogger(getClass());

    public ResponseEntity<MessageResponse> createItem(ItemAddRequest request) {
        try {
            Items newItem = new Items();
            newItem.setItemsName(request.getItemName());
            newItem.setItemsCode(request.getItemCode());
            newItem.setPrice(request.getPrice());
            newItem.setStock(request.getStock());
            newItem.setIsAvailable(1);
            
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            newItem.setLastRestock(timestamp);
            
            itemsRepository.save(newItem);

            String message = "Item added successfully!";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> updateItem(Integer itemId, ItemUpdateRequest request) {
        try {
            Optional<Items> optionalItem = itemsRepository.findById(itemId);
            if (!optionalItem.isPresent()) {
                String message = "Item not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            Items item = optionalItem.get();
            item.setItemsName(request.getItemName());
            item.setItemsCode(request.getItemCode());
            item.setPrice(request.getPrice());
            item.setStock(request.getStock());
            item.setIsAvailable(request.getIsAvailable());

            if (item.getStock() > request.getStock()) {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                item.setLastRestock(timestamp);
            }

            itemsRepository.save(item);

            String message = "update item success!";
            return ResponseEntity.ok(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<ItemListResponse> getAllItem(){
        try {
            List<Items> items = itemsRepository.findAll();

            List<ItemDetailResponse> itemList = items.stream().map(item ->
                    new ItemDetailResponse(
                            item.getItemsId(),
                            item.getItemsName(),
                            item.getItemsCode(),
                            item.getStock(),
                            item.getPrice(),
                            item.getIsAvailable(),
                            item.getLastRestock()
                    )
            ).collect(Collectors.toList());

            long totalData = items.size();
            log.info("Total items retrieved: {}", totalData);
            String message = "Get items success!";

            return ResponseEntity.ok()
                    .body(new ItemListResponse(totalData, itemList, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));

        } catch (Exception e) {
            log.error("Internal server error", e);
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ItemListResponse(0, null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<ItemDetailResponseDto> getItemByID(Integer itemId) {
        try {
            Optional<Items> optionalItem = itemsRepository.findById(itemId);
            if (!optionalItem.isPresent()) {
                String message = "Item not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ItemDetailResponseDto(null, message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            Items item = optionalItem.get();

            ItemDetailResponse itemDetail = new ItemDetailResponse(
                    item.getItemsId(),
                    item.getItemsName(),
                    item.getItemsCode(),
                    item.getStock(),
                    item.getPrice(),
                    item.getIsAvailable(),
                    item.getLastRestock()
            );

            String message = "Get item by ID success";
            return ResponseEntity.ok()
                    .body(new ItemDetailResponseDto(itemDetail, message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ItemDetailResponseDto(null, message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }

    public ResponseEntity<MessageResponse> deleteItemByID(Integer itemId) {
        try {
            Optional<Items> optionalItem = itemsRepository.findById(itemId);
            if (!optionalItem.isPresent()) {
                String message = "Item not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(message, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()));
            }

            itemsRepository.deleteById(itemId);

            String message = "Item deleted successfully";
            return ResponseEntity.ok()
                    .body(new MessageResponse(message, HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()));
        } catch (Exception e) {
            String message = messageSource.getMessage("internal.error", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
        }
    }
}