package ru.kviak.cofeeorder.cofeeorder.service.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderCancelledEventDto;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderConfirmedEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderConfirmedEventService;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderInvalidStatusException;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderConfirmedEventServiceTest {
    @Mock
    private EventRepository eventRepository;
    private OrderConfirmedEventService orderConfirmedEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderConfirmedEventService = new OrderConfirmedEventService(eventRepository);
    }

    @Test
    void testPublishEvent() {
        UUID uuid = UUID.randomUUID();
        OrderConfirmedEventDto orderEventDto = new OrderConfirmedEventDto(uuid, uuid, uuid, List.of(uuid), BigDecimal.TEN); // Add new Event.
        OrderEntity expectedOrderEntity;
        OrderEntity fromDb = new OrderEntity();
        fromDb.setStatus(OrderStatus.REGISTERED);

        when(eventRepository.findTopByOrderIdOrderByDateTimeDesc(uuid))
                .thenReturn(Optional.of(fromDb));
        expectedOrderEntity = OrderEntityMapper.INSTANCE.
                convertCustom(orderEventDto, fromDb);

        when(eventRepository.save(any(OrderEntity.class)))
                .thenReturn(expectedOrderEntity);
        OrderEntity result = orderConfirmedEventService
                .publishEvent(orderEventDto);
        verify(eventRepository, times(1))
                .save(any(OrderEntity.class));

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("expectedIssueTime")
                .isEqualTo(expectedOrderEntity);
    }

    @Test
    void testExpectedOrderInvalidStatusExceptionPublish(){
        UUID uuid = UUID.randomUUID();
        OrderEntity order = new OrderEntity();
        order.setStatus(OrderStatus.CANCELLED); // We take incorrect event.
        OrderCancelledEventDto orderEventDto = new OrderCancelledEventDto(uuid, uuid, "Test reason!");

        when(eventRepository.findTopByOrderIdOrderByDateTimeDesc(uuid))
                .thenReturn(Optional.of(order));
        // If put incorrect object should throw OrderInvalidStatusException
        OrderInvalidStatusException thrown = Assertions.assertThrows(OrderInvalidStatusException.class, () ->
                orderConfirmedEventService.publishEvent(orderEventDto));

        Assertions.assertEquals(OrderInvalidStatusException.class ,thrown.getClass());
    }

    @Test
    void testExpectedOrderOrderNotFoundExceptionPublish(){
        UUID uuid = UUID.randomUUID();
        OrderCancelledEventDto orderEventDto = new OrderCancelledEventDto(uuid, uuid, "Test reason!");

        when(eventRepository.findTopByOrderIdOrderByDateTimeDesc(uuid))
                .thenReturn(Optional.empty());
        OrderNotFoundException thrown = Assertions.assertThrows(OrderNotFoundException.class, () ->
                orderConfirmedEventService.publishEvent(orderEventDto));

        Assertions.assertEquals(OrderNotFoundException.class ,thrown.getClass());
    }
}
