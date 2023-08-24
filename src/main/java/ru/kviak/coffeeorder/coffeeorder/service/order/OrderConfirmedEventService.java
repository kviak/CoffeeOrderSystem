package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderInvalidStatusException;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;


@Service
@RequiredArgsConstructor
public class OrderConfirmedEventService extends AbstractOrderService<OrderConfirmedEventDto> {
    private final EventRepository eventRepository;

    @Override
    public Class<OrderConfirmedEventDto> getType() {
        return OrderConfirmedEventDto.class;
    }

    @Override
    @Transactional
    public OrderEntity publishEvent(OrderEventDto event) {
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId()).orElseThrow(OrderNotFoundException::new);
        if (order.getStatus() == OrderStatus.REGISTERED) {
            OrderEntity orderEntity = OrderEntityMapper.INSTANCE.convertCustom((OrderConfirmedEventDto) event, order);
            eventRepository.save(orderEntity);
            return orderEntity;
        }
        throw new OrderInvalidStatusException();
    }
}
