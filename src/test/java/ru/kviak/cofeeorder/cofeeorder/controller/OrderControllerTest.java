package ru.kviak.cofeeorder.cofeeorder.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.kviak.coffeeorder.coffeeorder.controller.OrderController;
import ru.kviak.coffeeorder.coffeeorder.dto.OrderRegisteredEventDto;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderRegisteredEventService;
import ru.kviak.coffeeorder.coffeeorder.service.order.OrderServiceProvider;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderServiceProvider orderServiceProvider;
    @Mock
    private OrderRegisteredEventService orderRegisteredEventService;
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderServiceProvider, orderRegisteredEventService);
    }

    @Test
    void testGet(){
        UUID uuid = UUID.randomUUID();
        OrderEntity order = new OrderEntity();

        when(orderRegisteredEventService.findOrder(uuid)).thenReturn(order);
        ResponseEntity<OrderEntity> responseActual = orderController.get(uuid.toString());

        Assertions.assertEquals(ResponseEntity.ok(order), responseActual);
    }

    @Test
    void testHandle(){
        OrderEntity order = new OrderEntity();
        OrderRegisteredEventDto or = new OrderRegisteredEventDto();

        when(orderServiceProvider.get(OrderRegisteredEventDto.class))
                .thenReturn(orderRegisteredEventService);
        when(orderRegisteredEventService.publishEvent(or))
                .thenReturn(order);

        Assertions.assertEquals(ResponseEntity.ok(order), orderController.handle(or));
    }

}
