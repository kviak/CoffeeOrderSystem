package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderIssuedByEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderIssuedByEventService implements OrderService<OrderIssuedByEventDto>{
    private final EventRepository eventRepository;

    @Override
    public Class<OrderIssuedByEventDto> getType() {
        return OrderIssuedByEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto event) {

        OrderIssuedByEventDto eventDto = (OrderIssuedByEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.ISSUED);

        OrderEntity order = eventRepository.getByOrderIdAndStatus(eventDto.getOrderId(), OrderStatus.READY);

        orderEntity.setClientId(order.getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setProductIds(order.getProductIds());

        if (order.getStatus() != OrderStatus.READY) return new OrderEntity();

        eventRepository.save(orderEntity);
        return orderEntity;

    }
}
