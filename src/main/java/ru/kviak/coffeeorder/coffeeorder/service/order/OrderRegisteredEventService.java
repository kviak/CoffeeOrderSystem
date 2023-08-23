package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.coffeeorder.util.OrderNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderRegisteredEventService extends AbstractOrderService<OrderRegisteredEventDto> implements FindOrderService {

    private final EventRepository eventRepository;

    @Override
    public Class<OrderRegisteredEventDto> getType() {
        return OrderRegisteredEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto OrderEvent) { //TODO: Rework, less hardcode
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

    @Override
    public OrderEntity findOrder(UUID id) {
        Optional<OrderEntity> order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(id);
        return order.orElseThrow(OrderNotFoundException::new);
    }
}
