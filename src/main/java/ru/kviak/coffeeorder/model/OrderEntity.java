package ru.kviak.coffeeorder.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity extends BaseEntity {

    @Column(name = "order_id", nullable = false)
    private UUID orderId; // Не изменяется

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "product_ids", nullable = false)
    private List<UUID> productIds;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "expected_issue_time", nullable = false)
    private Instant expectedIssueTime;

    @Column(name = "order_cancelling_reason")
    private String orderCancellingReason;

}
