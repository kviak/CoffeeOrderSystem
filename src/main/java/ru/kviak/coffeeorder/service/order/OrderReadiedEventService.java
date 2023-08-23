package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderReadiedEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderReadiedEventService implements OrderService<OrderReadiedEventDto>{

    private final EventRepository eventRepository;
    @Override
    public Class<OrderReadiedEventDto> getType() {
        return OrderReadiedEventDto.class;
    }

    @Transactional
    @Override
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode

        OrderReadiedEventDto eventDto = (OrderReadiedEventDto) event;
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(eventDto.getOrderId());
        System.out.println(order.getId());
        System.out.println(order.getStatus());
        if (order.getStatus() == OrderStatus.CONFIRM) {
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

    @Override
    public Optional<List<OrderEntity>> findOrder(UUID id) {
        return null; // TODO: Rework. SOLID, I - interface segregation.
    }
}
