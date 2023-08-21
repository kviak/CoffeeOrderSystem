package ru.kviak.coffeeorder.dto;

import lombok.Data;

@Data
public abstract class OrderEventDto {
    public abstract Long getOrderId();
    public abstract long getEmployeeId();
}
