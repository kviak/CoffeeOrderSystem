package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderConfirmedEventService extends AbstractOrderService<OrderConfirmedEventDto> {
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
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId()).orElseThrow();

        if (order.getStatus() == OrderStatus.REGISTERED) {
            orderEntity.setOrderId(eventDto.getOrderId());
            orderEntity.setEmployeeId(eventDto.getEmployeeId());
            orderEntity.setStatus(OrderStatus.CONFIRMED);
            orderEntity.setClientId(order.getClientId());
            orderEntity.setExpectedIssueTime(Instant.now());
            orderEntity.setPrice(order.getPrice());
            orderEntity.setProductIds(order.getProductIds());

            eventRepository.save(orderEntity);
            return orderEntity;
        }
        return new OrderEntity();
    }
}
