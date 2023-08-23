package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderIssuedByEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderIssuedByEventService implements OrderService<OrderIssuedByEventDto>{
    private final EventRepository eventRepository;

    @Override
    public Class<OrderIssuedByEventDto> getType() {
        return OrderIssuedByEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode

        OrderIssuedByEventDto eventDto = (OrderIssuedByEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId());

        if (order.getStatus() == OrderStatus.READY) {
            orderEntity.setOrderId(eventDto.getOrderId());
            orderEntity.setEmployeeId(eventDto.getEmployeeId());
            orderEntity.setStatus(OrderStatus.ISSUED);

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
        return null; // TODO: Rework. SOLID, I - interface segregation.
    }
}
