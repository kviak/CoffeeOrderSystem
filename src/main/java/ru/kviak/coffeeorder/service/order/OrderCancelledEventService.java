package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.dto.OrderCancelledEventDto;
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
public class OrderCancelledEventService implements OrderService<OrderCancelledEventDto> {

    private final EventRepository eventRepository;

    @Override
    public Class<OrderCancelledEventDto> getType() {
        return OrderCancelledEventDto.class;
    }

    @Override
    @Transactional
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode
        OrderCancelledEventDto orderCancelledEventDto = (OrderCancelledEventDto) event;
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId());
        if(order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.ISSUED) return new OrderEntity();
         else {OrderEntity orderEntity = new OrderEntity();

            orderEntity.setOrderId(orderCancelledEventDto.getOrderId());
            orderEntity.setEmployeeId(orderCancelledEventDto.getEmployeeId());
            orderEntity.setStatus(OrderStatus.CANCELLED);
            orderEntity.setOrderCancellingReason(orderCancelledEventDto.getReason());

            orderEntity.setClientId(order.getClientId());
            orderEntity.setExpectedIssueTime(Instant.now());
            orderEntity.setPrice(order.getPrice());
            orderEntity.setProductIds(order.getProductIds());

            eventRepository.save(orderEntity);
            return orderEntity;
         }
    }

    @Override
    public Optional<List<OrderEntity>> findOrder(UUID id) {
        return null; // TODO: Rework. SOLID, I - interface segregation.
    }
}
