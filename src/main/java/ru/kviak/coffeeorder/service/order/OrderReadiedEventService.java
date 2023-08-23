package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderReadiedEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderReadiedEventService implements OrderService<OrderReadiedEventDto>{

    private final EventRepository eventRepository;
    @Override
    public Class<OrderReadiedEventDto> getType() {
        return OrderReadiedEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto event) {

        OrderReadiedEventDto eventDto = (OrderReadiedEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.READY);

        OrderEntity order = eventRepository.getByOrderIdAndStatus(eventDto.getOrderId(), OrderStatus.CONFIRM);

        orderEntity.setClientId(order.getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setProductIds(order.getProductIds());

        if (order.getStatus() != OrderStatus.CONFIRM) return new OrderEntity();

        eventRepository.save(orderEntity);
        return orderEntity;

    }
}
