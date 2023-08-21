package ru.kviak.coffeeorder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kviak.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.model.EventOrder;
import ru.kviak.coffeeorder.service.order.OrderRegisteredEventService;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class EventController {

    private final OrderRegisteredEventService orderRegisteredEventService;

    @PostMapping(value = "/create")
    public EventOrder index(@RequestBody OrderRegisteredEventDto orderRegisteredEventDto){
        System.out.println(orderRegisteredEventDto.toString());
        return orderRegisteredEventService.publishEvent(orderRegisteredEventDto);
    }

    @GetMapping("/s")
    public void s(){
        System.out.println(new OrderRegisteredEventDto());
    }
}
