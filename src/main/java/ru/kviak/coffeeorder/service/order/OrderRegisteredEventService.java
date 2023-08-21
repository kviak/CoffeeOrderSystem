package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.model.EventOrder;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderRegisteredEventService implements OrderService<OrderRegisteredEventDto>{

    private final EventRepository eventRepository;
    @Override
    public EventOrder publishEvent(OrderRegisteredEventDto event) {

        EventOrder eventOrder = new EventOrder();
        eventOrder.setOrderId(UUID.randomUUID());
        eventOrder.setStreamId(UUID.randomUUID());
        eventOrder.setStatus(OrderStatus.REGISTERED);
        eventOrder.setTime(LocalDateTime.now());

        System.out.println(eventOrder);
        eventRepository.save(eventOrder);
        return eventOrder;
    }
}
