package ru.kviak.coffeeorder.coffeeorder.service.order;

import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;

public abstract class AbstractOrderService <T extends OrderEventDto> implements OrderService<T> {}
