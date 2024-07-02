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

import com.tujuhsembilan.webStore.dto.request.ItemAddRequest;
import com.tujuhsembilan.webStore.dto.request.ItemUpdateRequest;
import com.tujuhsembilan.webStore.dto.response.ItemDetailResponseDto;
import com.tujuhsembilan.webStore.dto.response.ItemListResponse;
import com.tujuhsembilan.webStore.dto.response.MessageResponse;
import com.tujuhsembilan.webStore.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createItem(@RequestBody ItemAddRequest request) {
        return itemService.createItem(request);
    }

    @PutMapping("/update/{itemsId}")
    public ResponseEntity<MessageResponse> updateItem(@PathVariable Integer itemsId, @RequestBody ItemUpdateRequest request) {
        return itemService.updateItem(itemsId, request);
    }
    
    @GetMapping("/all")
    public ResponseEntity<ItemListResponse> getAllItem() {
        return itemService.getAllItem();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDetailResponseDto> getItemByID(@PathVariable Integer itemId) {
        return itemService.getItemByID(itemId);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<MessageResponse> deleteItemByID(@PathVariable Integer itemId) {
        return itemService.deleteItemByID(itemId);
    }
}
