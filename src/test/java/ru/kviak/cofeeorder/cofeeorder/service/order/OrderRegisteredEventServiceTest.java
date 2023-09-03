package ru.kviak.cofeeorder.cofeeorder.service.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.repository.EventRepository;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderRegisteredEventService;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderInvalidStatusException;
import ru.kviak.coffeeorder.coffeeorder.util.error.OrderNotFoundException;
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderRegisteredEventServiceTest {

    @Mock
    private EventRepository eventRepository;
    private OrderRegisteredEventService orderRegisteredEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderRegisteredEventService = new OrderRegisteredEventService(eventRepository);
    }

    @Test
    void testPublishEvent() {
        OrderRegisteredEventDto orderEventDto = new OrderRegisteredEventDto(UUID.randomUUID(), UUID.randomUUID(), List.of(UUID.randomUUID()), BigDecimal.TEN); // Add new Event.
        OrderEntity expectedOrderEntity;
        expectedOrderEntity = OrderEntityMapper.INSTANCE.
                convertCustom(orderEventDto);
        when(eventRepository.save(any(OrderEntity.class)))
                .thenReturn(expectedOrderEntity);
        OrderEntity result = orderRegisteredEventService
                .publishEvent(orderEventDto);
        verify(eventRepository, times(1))
                .save(any(OrderEntity.class));
        assertEquals(expectedOrderEntity.getStatus(), result.getStatus());
    }

    @Test
    void testFindOrder() {
        UUID orderId = UUID.randomUUID();
        OrderEntity expectedOrderEntity = new OrderEntity();
        when(eventRepository.findTopByOrderIdOrderByDateTimeDesc(orderId))
                .thenReturn(Optional.of(expectedOrderEntity));
        OrderEntity result = orderRegisteredEventService.findOrder(orderId);

        verify(eventRepository, times(1))
                .findTopByOrderIdOrderByDateTimeDesc(orderId);
        assertEquals(expectedOrderEntity, result);
    }

    @Test
    void testExpectedExceptionFindOrder(){
        OrderNotFoundException thrown = Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderRegisteredEventService.findOrder(UUID.fromString("227e4229-3a4a-4ad9-8af0-54fe3a9d1476")); // If we find non-existent order throw OrderNotFoundException
        });
        Assertions.assertEquals(OrderNotFoundException.class ,thrown.getClass());
    }

    @Test
    void testExpectedExceptionPublish(){
        OrderRegisteredEventDto orderEventDto = new OrderRegisteredEventDto(); // If put empty object should throw OrderInvalidStatusException
        OrderInvalidStatusException thrown = Assertions.assertThrows(OrderInvalidStatusException.class, () ->
            orderRegisteredEventService.publishEvent(orderEventDto));
        Assertions.assertEquals(OrderInvalidStatusException.class ,thrown.getClass());
    }
}
