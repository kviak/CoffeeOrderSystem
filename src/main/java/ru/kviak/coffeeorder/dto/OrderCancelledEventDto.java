package ru.kviak.coffeeorder.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
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
