package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderInvalidStatusException;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;

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
    public OrderEntity publishEvent(OrderEventDto event) {
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId()).orElseThrow(OrderNotFoundException::new);

        if(order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.ISSUED) {
            throw(new OrderInvalidStatusException());}
        else {
            OrderEntity orderEntity = OrderEntityMapper.INSTANCE.convertCustom((OrderCancelledEventDto) event, order);
            eventRepository.save(orderEntity);
            return orderEntity;
        }
    }
}
