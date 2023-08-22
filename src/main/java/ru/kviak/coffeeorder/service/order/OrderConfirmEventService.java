package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderConfirmEventService implements OrderService<OrderConfirmedEventDto> {
    private final EventRepository eventRepository;

    @Override
    public Class<OrderConfirmedEventDto> getType() {
        return OrderConfirmedEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderConfirmedEventDto event) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(event.getOrderId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setStatus(OrderStatus.REGISTERED);
        orderEntity.setClientId(event.getClientId());
        orderEntity.setProductIds(event.getProductIds());
        orderEntity.setEmployeeId(event.getEmployeeId());
        orderEntity.setPrice(event.getPrice());

        return orderEntity;
    }


}
