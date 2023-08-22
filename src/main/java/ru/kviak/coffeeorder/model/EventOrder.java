package ru.kviak.coffeeorder.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.kviak.coffeeorder.dto.OrderEventDto;
import ru.kviak.coffeeorder.dto.OrderRegisteredEventDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "events")
public class EventOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID streamId;

    @Column(name = "order_id")
    private UUID orderId; // Не изменяется

    @Column(name = "version")
    private long version;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "data")
    @JdbcTypeCode(SqlTypes.JSON)
    private OrderEventDto orderEventDto;
}
