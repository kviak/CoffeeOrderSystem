package ru.kviak.coffeeorder.service.order;

import org.springframework.stereotype.Service;
import ru.kviak.coffeeorder.dto.OrderEventDto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceProvider {

    private final Map<Class<? extends OrderEventDto>, OrderService<?>> orderServices;
    public OrderServiceProvider(List<OrderService<?>> orderServices){
        this.orderServices = orderServices.stream()
                .collect(Collectors.toMap(OrderService::getType, Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <T extends OrderEventDto> OrderService<T> get(Class<T> aClass){
        return (OrderService<T>) this.orderServices.get(aClass);
    }
}
