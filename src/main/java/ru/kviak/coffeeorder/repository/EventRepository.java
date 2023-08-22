package ru.kviak.coffeeorder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.coffeeorder.model.EventOrder;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventOrder, UUID> {

    EventOrder getByOrderId(UUID uuid);
}
