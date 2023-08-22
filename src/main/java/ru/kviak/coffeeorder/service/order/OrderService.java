package ru.kviak.coffeeorder.service.order;

import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.EventOrder;

public interface OrderService<T extends OrderEventDto> {

    EventOrder publishEvent(T event);

    //Order findOrder(int id);
}
