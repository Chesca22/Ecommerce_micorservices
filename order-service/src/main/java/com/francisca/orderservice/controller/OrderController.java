package com.francisca.orderservice.controller;

import com.francisca.orderservice.dto.OrderLineItemDto;
import com.francisca.orderservice.dto.OrderRequest;
import com.francisca.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
//    @CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallbackMethod")
//    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("Order Placed Successfully");
      return   CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));

    }

    public CompletableFuture<String> placeOrderFallbackMethod(OrderRequest orderRequest, RuntimeException e){
        log.error("Placing order Failed");
        return CompletableFuture.supplyAsync(()->"Placing order failed, try again later") ;
    }
}
