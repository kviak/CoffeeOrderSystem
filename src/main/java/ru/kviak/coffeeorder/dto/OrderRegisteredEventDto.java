package ru.kviak.coffeeorder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRegisteredEventDto extends OrderEventDto {

    private long employeeId;
    private long clientId;
    private LocalDateTime localDateTime;
    private long productId;
    private long price;

    @Override
    public Long getOrderId() {
        return null;
    }
}
