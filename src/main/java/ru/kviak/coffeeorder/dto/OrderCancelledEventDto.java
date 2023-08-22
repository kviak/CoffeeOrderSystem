package ru.kviak.coffeeorder.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@JsonTypeName(value ="cancel")
public class OrderCancelledEventDto extends OrderEventDto{

    private UUID orderId;
    private long employeeId;
    private String reason;

    @Override
    public long getEmployeeId() {
        return employeeId;
    }
}
