package ru.kviak.coffeeorder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kviak.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.model.EventOrder;
import ru.kviak.coffeeorder.service.order.OrderCancelledEventService;
import ru.kviak.coffeeorder.service.order.OrderRegisteredEventService;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class EventController {

    private final OrderRegisteredEventService orderRegisteredEventService;
    private final OrderCancelledEventService orderCancelledEventService;

    @PostMapping(value = "/create")
    public EventOrder create(@RequestBody OrderRegisteredEventDto orderRegisteredEventDto){
        System.out.println(orderRegisteredEventDto.toString());
        return orderRegisteredEventService.publishEvent(orderRegisteredEventDto);
    }

    @PostMapping(value = "/cancel")
    public EventOrder cancel(@RequestBody OrderCancelledEventDto orderCancelledEventDto){
        System.out.println(orderCancelledEventDto.toString());
        return orderCancelledEventService.publishEvent(orderCancelledEventDto);
    }

    @GetMapping("/s")
    public void s(){
        System.out.println(new OrderRegisteredEventDto());
    }
}
