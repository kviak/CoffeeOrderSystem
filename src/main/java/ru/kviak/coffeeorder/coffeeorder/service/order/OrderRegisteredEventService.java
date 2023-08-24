package ru.kviak.coffeeorder.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderRegisteredEventService extends AbstractOrderService<OrderRegisteredEventDto> implements FindOrderService {

    private final EventRepository eventRepository;

    @Override
    public Class<OrderRegisteredEventDto> getType() {
        return OrderRegisteredEventDto.class;
    }

    @Transactional
    @Override
    public OrderEntity publishEvent(OrderEventDto OrderEvent) {
        OrderEntity orderEntity = OrderEntityMapper.INSTANCE.convertCustom((OrderRegisteredEventDto) OrderEvent);
        eventRepository.save(orderEntity);
        return orderEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public OrderEntity findOrder(UUID id) {
        Optional<OrderEntity> order = eventRepository.findTopByOrderIdOrderByDateTimeDesc(id);
        return order.orElseThrow(OrderNotFoundException::new);
    }
}
