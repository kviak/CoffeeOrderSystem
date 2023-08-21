package ru.kviak.coffeeorder.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.coffeeorder.model.EventOrder;

public interface EventRepository extends JpaRepository<EventOrder, Integer> {}
