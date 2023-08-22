package ru.kviak.coffeeorder.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "eventName", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderRegisteredEventDto.class, name = "OrderRegisteredEvent"),
        @JsonSubTypes.Type(value = OrderCancelledEventDto.class, name = "OrderCancelledEvent"),
        @JsonSubTypes.Type(value = OrderConfirmedEventDto.class, name = "OrderConfirmedEvent")
})
public abstract class OrderEventDto implements Event {
    public abstract UUID getOrderId();
    public abstract UUID getEmployeeId();
}
