package ru.kviak.coffeeorder.coffeeorder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findTopByOrderIdOrderByDateTimeDesc(UUID uuid);

}
