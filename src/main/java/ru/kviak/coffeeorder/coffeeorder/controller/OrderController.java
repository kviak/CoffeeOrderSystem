package ru.kviak.coffeeorder.coffeeorder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderRegisteredEventService;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderServiceProvider;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderErrorResponse;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderInvalidStatusException;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;

import java.time.Instant;
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
                .findOrder(UUID.fromString(id)));} catch (Exception e) {
            throw new OrderNotFoundException();
        }
    }

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<OrderErrorResponse> handleOrderNotFoundException(){
        OrderErrorResponse response = new OrderErrorResponse(
                "Order with this id wasn't found!",
                Instant.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OrderInvalidStatusException.class)
    private ResponseEntity<OrderErrorResponse> handleOrderInvalidStatusException(){
        OrderErrorResponse response = new OrderErrorResponse(
                "Invalid event for this order!",
                Instant.now()
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
