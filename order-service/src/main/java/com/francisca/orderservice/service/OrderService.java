package com.francisca.orderservice.service;


import com.francisca.orderservice.dto.InventoryResponse;
import com.francisca.orderservice.dto.OrderLineItemDto;
import com.francisca.orderservice.dto.OrderRequest;
import com.francisca.orderservice.event.OrderPlacedEvent;
import com.francisca.orderservice.model.Order;
import com.francisca.orderservice.model.OrderLineItem;
import com.francisca.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

   // private final NewTopic topic;
    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDto()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemList().stream()
                .map(OrderLineItem::getSkuCode).toList();

//        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
//try(Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())){
//inventoryServiceLookup.tag("call", "inventory-service");
        //Call inventory service to check if product is in stock, then place order if in stock
       InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                        .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
           orderRepository.save(order);
              kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
           return "Order Placed Successfully";
       }
        else{
            throw new IllegalArgumentException("Order cannot be placed, product is out of stock");
       }
//    }
//    finally {
//        inventoryServiceLookup.end();
//    }
}

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto){
        return OrderLineItem.builder()
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .skuCode(orderLineItemDto.getSkuCode())
                .build();

       // return orderLineItem;

    }

}
