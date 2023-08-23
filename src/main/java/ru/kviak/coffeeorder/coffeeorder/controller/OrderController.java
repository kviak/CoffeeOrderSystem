package ru.kviak.coffeeorder.coffeeorder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderRegisteredEventService;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderServiceProvider;
import ru.kviak.coffeeorder.coffeeorder.util.OrderErrorResponse;
import ru.kviak.coffeeorder.coffeeorder.util.OrderNotFoundException;

import java.util.UUID;

@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
@RestController
public class OrderController {

    private final OrderServiceProvider orderServiceProvider;
    private final OrderRegisteredEventService orderRegisteredEventService;

    @PostMapping
    public ResponseEntity<OrderEntity> handle(@RequestBody OrderEventDto dto){
        log.info("{}",dto.toString());
        return ResponseEntity.ok(orderServiceProvider.get(dto.getClass()).publishEvent(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> get(@PathVariable String id){
        try{
        return ResponseEntity.ok(orderRegisteredEventService
                .findOrder(UUID.fromString(id.substring(3))));} catch (Exception e) {
            throw new OrderNotFoundException();
        }
    }

    @ExceptionHandler
    private ResponseEntity<OrderErrorResponse> handleException(OrderNotFoundException e){
        OrderErrorResponse response = new OrderErrorResponse(
                "Order with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
