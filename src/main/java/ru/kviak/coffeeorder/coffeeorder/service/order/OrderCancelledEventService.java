package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderCancelledEventService extends AbstractOrderService<OrderCancelledEventDto> {

    private final EventRepository eventRepository;

    @Override
    public Class<OrderCancelledEventDto> getType() {
        return OrderCancelledEventDto.class;
    }

    @Override
    @Transactional
    public OrderEntity publishEvent(OrderEventDto event) { //TODO: Rework, less hardcode
        OrderCancelledEventDto orderCancelledEventDto = (OrderCancelledEventDto) event;
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId()).orElseThrow();
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
}
