package ru.kviak.coffeeorder.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "type") @JsonSubTypes({

        @JsonSubTypes.Type(value = OrderRegisteredEventDto.class, name = "regist"),
        @JsonSubTypes.Type(value = OrderCancelledEventDto.class, name = "cancel")
})
public abstract class OrderEventDto {
    //public abstract Long getOrderId();
    public abstract long getEmployeeId();
}
