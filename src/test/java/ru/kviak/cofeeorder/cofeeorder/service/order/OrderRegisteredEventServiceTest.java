package ru.kviak.cofeeorder.cofeeorder.service.order;

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
import ru.kviak.coffeeorder.coffeeorder.util.mapper.OrderEntityMapper;

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

    private OrderEntityMapper mapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderRegisteredEventService = new OrderRegisteredEventService(eventRepository);
    }

    @Test
    public void testPublishEvent() {
        // Создайте фиктивный объект OrderEventDto
        OrderRegisteredEventDto orderEventDto = new OrderRegisteredEventDto(); // Здесь вам нужно создать объект, который будет использоваться в вашем тесте
        OrderEntity expectedOrderEntity;

        expectedOrderEntity = OrderEntityMapper.INSTANCE.convertCustom(orderEventDto);

        // Определите ожидаемое поведение для eventRepository.save()
        when(eventRepository.save(any(OrderEntity.class))).thenReturn(expectedOrderEntity);

        // Вызовите метод publishEvent
        OrderEntity result = orderRegisteredEventService.publishEvent(orderEventDto);

        // Проверьте, что eventRepository.save() был вызван с правильными аргументами
        verify(eventRepository, times(1)).save(any(OrderEntity.class));

        // Проверьте, что результат совпадает с ожидаемым значением
        assertEquals(expectedOrderEntity.getStatus(), result.getStatus());
    }

    @Test
    public void testFindOrder() {
        UUID orderId = UUID.randomUUID();

        // Определите ожидаемое поведение для eventRepository.findTopByOrderIdOrderByDateTimeDesc()
        OrderEntity expectedOrderEntity = new OrderEntity();
        when(eventRepository.findTopByOrderIdOrderByDateTimeDesc(orderId)).thenReturn(Optional.of(expectedOrderEntity));

        // Вызовите метод findOrder
        OrderEntity result = orderRegisteredEventService.findOrder(orderId);

        // Проверьте, что eventRepository.findTopByOrderIdOrderByDateTimeDesc() был вызван с правильными аргументами
        verify(eventRepository, times(1)).findTopByOrderIdOrderByDateTimeDesc(orderId);

        // Проверьте, что результат совпадает с ожидаемым значением
        assertEquals(expectedOrderEntity, result);
    }
}
