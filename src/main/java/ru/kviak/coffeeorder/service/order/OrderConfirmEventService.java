package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public OrderEntity publishEvent(OrderEventDto event) {
        OrderConfirmedEventDto eventDto = (OrderConfirmedEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.CONFIRM);
        orderEntity.setClientId(eventRepository.getByOrderId(eventDto.getOrderId()).getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(eventRepository.getByOrderId(eventDto.getOrderId()).getPrice());
        orderEntity.setProductIds(eventRepository.getByOrderId(eventDto.getOrderId()).getProductIds());

        if (eventRepository.getByOrderId(event.getOrderId()).getStatus() != OrderStatus.REGISTERED) return new OrderEntity();

        eventRepository.save(orderEntity);
        return orderEntity;

    }
}
