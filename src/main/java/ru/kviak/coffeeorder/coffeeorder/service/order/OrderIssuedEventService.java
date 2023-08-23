package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderIssuedByEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderIssuedEventService extends AbstractOrderService<OrderIssuedByEventDto> {
    private final EventRepository eventRepository;

    @Override
    public Class<OrderIssuedByEventDto> getType() {
        return OrderIssuedByEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode

        OrderIssuedByEventDto eventDto = (OrderIssuedByEventDto) event;
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId()).orElseThrow();

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
}
