package ru.kviak.coffeeorder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.coffeeorder.model.OrderEntity;

import java.util.UUID;

public interface EventRepository extends JpaRepository<OrderEntity, UUID> {

    OrderEntity getByOrderId(UUID uuid);
}
