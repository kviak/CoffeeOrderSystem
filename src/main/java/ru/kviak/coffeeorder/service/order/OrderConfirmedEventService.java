package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderConfirmedEventService implements OrderService<OrderConfirmedEventDto> {
    private final EventRepository eventRepository;

    @Override
    public Class<OrderConfirmedEventDto> getType() {
        return OrderConfirmedEventDto.class;
    }

    @Override
    @Transactional
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode

        OrderConfirmedEventDto eventDto = (OrderConfirmedEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId());

        if (order.getStatus() == OrderStatus.REGISTERED) {
            orderEntity.setOrderId(eventDto.getOrderId());
            orderEntity.setEmployeeId(eventDto.getEmployeeId());
            orderEntity.setStatus(OrderStatus.CONFIRM);
            orderEntity.setClientId(order.getClientId());
            orderEntity.setExpectedIssueTime(Instant.now());
            orderEntity.setPrice(order.getPrice());
            orderEntity.setProductIds(order.getProductIds());

            eventRepository.save(orderEntity);
            return orderEntity;
        }
        return new OrderEntity();
    }

    @Override
    public Optional<List<OrderEntity>> findOrder(UUID id) {
        return null;  // TODO: Rework. SOLID, I - interface segregation.
    }
}
