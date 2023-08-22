package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderCancelledEventService implements OrderService<OrderCancelledEventDto> {

    private final EventRepository eventRepository;

    @Override
    public Class<OrderCancelledEventDto> getType() {
        return OrderCancelledEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto event) {
        OrderCancelledEventDto orderCancelledEventDto = (OrderCancelledEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderCancelledEventDto.getOrderId());
        orderEntity.setEmployeeId(orderCancelledEventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.CANCELLED);
        orderEntity.setOrderCancellingReason(orderCancelledEventDto.getReason());
        orderEntity.setClientId(eventRepository.getByOrderId(orderCancelledEventDto.getOrderId()).getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(eventRepository.getByOrderId(orderCancelledEventDto.getOrderId()).getPrice());
        orderEntity.setProductIds(eventRepository.getByOrderId(orderCancelledEventDto.getOrderId()).getProductIds());

        eventRepository.save(orderEntity);
        return orderEntity;
    }
}
