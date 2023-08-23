package ru.kviak.coffeeorder.service.order;

import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService<T extends OrderEventDto> {

    Class<T> getType();
    OrderEntity publishEvent(OrderEventDto event);
    Optional<List<OrderEntity>> findOrder(UUID id);
}
