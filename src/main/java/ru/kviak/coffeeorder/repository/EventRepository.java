package ru.kviak.coffeeorder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;

import java.util.UUID;

public interface EventRepository extends JpaRepository<OrderEntity, UUID> {

    OrderEntity getByOrderId(UUID uuid);

    OrderEntity getByOrderIdAndStatus(UUID uuid, OrderStatus status);
}
