package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderIssuedEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderInvalidStatusException;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;

@Service
@RequiredArgsConstructor
public class OrderIssuedEventService extends AbstractOrderService<OrderIssuedEventDto> {
    private final EventRepository eventRepository;

    @Override
    public Class<OrderIssuedEventDto> getType() {
        return OrderIssuedEventDto.class;
    }

    @Transactional
    @Override
    public OrderEntity publishEvent(OrderEventDto event) {
        OrderEntity order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(event.getOrderId()).orElseThrow(OrderNotFoundException::new);
        if (order.getStatus() == OrderStatus.READY) {
            OrderEntity orderEntity = OrderEntityMapper.INSTANCE.convertCustom((OrderIssuedEventDto) event, order);
            eventRepository.save(orderEntity);
            return orderEntity;
        }
        throw new OrderInvalidStatusException();
    }
}
