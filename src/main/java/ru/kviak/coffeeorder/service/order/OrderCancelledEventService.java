package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.model.EventOrder;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderCancelledEventService {

    private final EventRepository eventRepository;

    public EventOrder publishEvent(OrderCancelledEventDto event) {

        EventOrder eventOrder = new EventOrder();
        eventOrder.setOrderId(event.getOrderId());
        eventOrder.setStreamId(UUID.randomUUID());
        eventOrder.setStatus(OrderStatus.CANCELLED);
        eventOrder.setTime(LocalDateTime.now());
        eventOrder.setOrderEventDto(event);
        eventOrder.setVersion(eventRepository.getByOrderId(event.getOrderId()).getVersion()+1);


        System.out.println(eventOrder);
        eventRepository.save(eventOrder);
        return eventOrder;
    }
}
