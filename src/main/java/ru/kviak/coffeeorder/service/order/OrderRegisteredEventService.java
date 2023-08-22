package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderRegisteredEventService implements OrderService<OrderRegisteredEventDto>{

    private final EventRepository eventRepository;

    @Override
    public Class<OrderRegisteredEventDto> getType() {
        return OrderRegisteredEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto OrderEvent) {
        OrderRegisteredEventDto event = (OrderRegisteredEventDto) OrderEvent;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(UUID.randomUUID());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setStatus(OrderStatus.REGISTERED);
        orderEntity.setClientId(event.getClientId());
        orderEntity.setProductIds(event.getProductIds());
        orderEntity.setEmployeeId(event.getEmployeeId());
        orderEntity.setPrice(event.getPrice());

        eventRepository.save(orderEntity);
        return orderEntity;
    }

}
