package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderRegisteredEventService implements OrderService<OrderRegisteredEventDto>{

    private final EventRepository eventRepository;

    @Override
    public Class<OrderRegisteredEventDto> getType() {
        return OrderRegisteredEventDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto eventDto) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(UUID.randomUUID());
        orderEntity.setClientId(UUID.randomUUID());
        orderEntity.setEmployeeId(UUID.randomUUID());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setProductIds(Collections.singletonList(UUID.randomUUID()));
        orderEntity.setPrice(BigDecimal.valueOf(200.00));
        orderEntity.setStatus(OrderStatus.REGISTERED);


        eventRepository.save(orderEntity);
        return orderEntity;
    }

}
