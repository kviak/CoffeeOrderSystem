package ru.kviak.coffeeorder.coffeeorder.util.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class OrderErrorResponse {
    private String message;
    private Instant instant;
}
