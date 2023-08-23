package ru.kviak.coffeeorder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.coffeeorder.model.OrderEntity;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<OrderEntity, UUID> {

    OrderEntity findTopByOrderIdOrderByDateTimeDesc(UUID uuid);

    List<OrderEntity> getAllByOrderId(UUID uuid);
}
