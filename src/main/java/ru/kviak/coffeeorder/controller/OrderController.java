package ru.kviak.coffeeorder.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.service.order.OrderServiceProvider;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderServiceProvider orderServiceProvider;

    @PostMapping
    public ResponseEntity<OrderEntity> handle(@RequestBody OrderEventDto dto){
        log.info("{}",dto.toString());
        return ResponseEntity.ok(orderServiceProvider.get(dto.getClass()).publishEvent(dto)); // TODO: should not return a db entity.
    }

    @PostMapping("/get") //TODO: try to combine methods.
    public ResponseEntity<?> get(@RequestBody OrderEventDto dto){
        log.info("{}",dto.toString());
        Optional<List<OrderEntity>> optional = orderServiceProvider.get(dto.getClass()).findOrder(dto.getOrderId());

        if (optional.get().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else return new ResponseEntity<>(orderServiceProvider.get(dto.getClass())
                .findOrder(dto.getOrderId()).get(), HttpStatus.OK);
    }
}
