package ru.kviak.coffeeorder.service.order;

import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;

public interface OrderService<T extends OrderEventDto> {

    Class<T> getType();
    OrderEntity publishEvent(OrderEventDto event);

    //Order findOrder(int id);
}
