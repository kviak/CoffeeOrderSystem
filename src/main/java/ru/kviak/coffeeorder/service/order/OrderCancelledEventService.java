package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class OrderCancelledEventService implements OrderService<OrderCancelledEventDto> {

    private final EventRepository eventRepository;

    @Override
    public Class<OrderCancelledEventDto> getType() {
        return OrderCancelledEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderCancelledEventDto event) {
        return null;
    }

}
