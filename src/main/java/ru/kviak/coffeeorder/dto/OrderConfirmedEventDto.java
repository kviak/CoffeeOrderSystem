package ru.kviak.coffeeorder.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmedEventDto extends OrderEventDto {

    private final String eventName="OrderConfirmedEvent";
    private UUID orderId;
    private UUID employeeId;
    private UUID clientId;
    private List<UUID> productIds;
    private BigDecimal price;

    @Override
    public UUID getEmployeeId() {
        return employeeId;
    }
}
