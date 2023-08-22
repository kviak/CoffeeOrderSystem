package ru.kviak.coffeeorder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.service.order.OrderServiceProvider;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderServiceProvider orderServiceProvider;

    @PostMapping
    public ResponseEntity<OrderEntity> handle(@RequestBody OrderEventDto dto){
        log.info("{}",dto.toString());
        //return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        return ResponseEntity.ok(orderServiceProvider.get(dto.getClass()).publishEvent(dto));
    }
}
