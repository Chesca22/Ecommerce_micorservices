package com.francisca.inventoryService.service;

import com.francisca.inventoryService.dto.InventoryResponse;
import com.francisca.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows // to avoid try catch block for Thread.sleep - not used in production
    public List<InventoryResponse> isInStock(List<String> skuCode) {
//        log.info("waiting started");
//        Thread.sleep(10000); // simulating timeout
//        log.info("waiting ended");
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .inStock(inventory.getQuantity() > 0)
                            .build()
                    ).toList();
    }
}