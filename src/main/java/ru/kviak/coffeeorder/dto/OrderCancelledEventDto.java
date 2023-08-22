package ru.kviak.coffeeorder.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelledEventDto extends OrderEventDto{

    private final String eventName="OrderCancelledEvent";
    private UUID orderId;
    private UUID employeeId;
    private String reason;

    @Override
    public UUID getEmployeeId() {
        return employeeId;
    }
}
