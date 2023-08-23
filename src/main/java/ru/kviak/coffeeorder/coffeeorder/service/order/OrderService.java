package ru.kviak.coffeeorder.coffeeorder.service.order;

import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;


public interface OrderService<T extends OrderEventDto> {

    Class<T> getType();
    OrderEntity publishEvent(OrderEventDto event);
}
