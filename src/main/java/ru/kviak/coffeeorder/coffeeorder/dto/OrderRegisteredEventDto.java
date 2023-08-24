package ru.kviak.coffeeorder.coffeeorder.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRegisteredEventDto extends OrderEventDto{

    private final String eventName="OrderRegisteredEvent";
    private UUID employeeId;
    private UUID clientId;
    private List<UUID> productIds;
    private BigDecimal price;


    @Override
    public UUID getOrderId() {
        return null;
    }
}
