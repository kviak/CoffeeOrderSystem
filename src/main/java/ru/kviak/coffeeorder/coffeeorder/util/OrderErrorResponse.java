package ru.kviak.coffeeorder.coffeeorder.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderErrorResponse {
    private String message;
    private long timestamp;
}
