package ru.kviak.coffeeorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetDto extends OrderEventDto{
    private UUID orderId;
    private final String eventName="OrderGet";

    @Override
    public UUID getEmployeeId() {
        return null;
    }
}
