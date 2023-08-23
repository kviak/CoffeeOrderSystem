package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderReadyEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderReadyEventService extends AbstractOrderService<OrderReadyEventDto>{

    private final EventRepository eventRepository;
    @Override
    public Class<OrderReadyEventDto> getType() {
        return OrderReadyEventDto.class;
    }

    @Transactional
    @Override
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode

        OrderReadyEventDto eventDto = (OrderReadyEventDto) event;
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(eventDto.getOrderId()).orElseThrow();
        System.out.println(order.getId());
        System.out.println(order.getStatus());
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId(eventDto.getOrderId());
            orderEntity.setEmployeeId(eventDto.getEmployeeId());
            orderEntity.setStatus(OrderStatus.READY);
            orderEntity.setClientId(order.getClientId());
            orderEntity.setExpectedIssueTime(Instant.now());
            orderEntity.setPrice(order.getPrice());
            orderEntity.setProductIds(order.getProductIds());
            eventRepository.save(orderEntity);
            return orderEntity;
        } return new OrderEntity();
    }
}
