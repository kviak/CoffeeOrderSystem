package ru.kviak.coffeeorder.coffeeorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderIssuedByEventDto extends OrderEventDto{

    private final String eventName = "OrderReadiedEvent";
    private UUID orderId;
    private UUID employeeId;

}
