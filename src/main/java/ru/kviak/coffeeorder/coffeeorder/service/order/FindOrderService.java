package ru.kviak.coffeeorder.coffeeorder.service.order;

import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;

import java.util.UUID;

public interface FindOrderService {
    OrderEntity findOrder(UUID id);
}
