package ru.kviak.coffeeorder.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderGetDto;
import ru.kviak.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderGetService implements OrderService<OrderGetDto>{
    private final EventRepository eventRepository;

    @Override
    public Class<OrderGetDto> getType() {
        return OrderGetDto.class;
    }

    @Override
    public OrderEntity publishEvent(OrderEventDto event) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<OrderEntity>> findOrder(UUID id) {
        return Optional.ofNullable(eventRepository.getAllByOrderId(id));
    }
}
