package ru.kviak.coffeeorder.coffeeorder.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kviak.coffeeorder.coffeeorder.dto.*;
import ru.kviak.coffeeorder.coffeeorder.model.OrderEntity;
import ru.kviak.coffeeorder.coffeeorder.model.OrderStatus;

import java.time.Instant;
import java.util.UUID;

@Mapper
public interface OrderEntityMapper {
    OrderEntityMapper INSTANCE = Mappers.getMapper(OrderEntityMapper.class);

    default OrderEntity convertCustom(OrderRegisteredEventDto eventDto){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(UUID.randomUUID());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setStatus(OrderStatus.REGISTERED);
        orderEntity.setClientId(eventDto.getClientId());
        orderEntity.setProductIds(eventDto.getProductIds());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setPrice(eventDto.getPrice());
        return orderEntity;
    }

    default OrderEntity convertCustom(OrderCancelledEventDto eventDto, OrderEntity order){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.CANCELLED);
        orderEntity.setOrderCancellingReason(eventDto.getReason());

        orderEntity.setClientId(order.getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setProductIds(order.getProductIds());
        return orderEntity;
    }

    default OrderEntity convertCustom(OrderConfirmedEventDto eventDto, OrderEntity order){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.CONFIRMED);
        orderEntity.setClientId(order.getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setProductIds(order.getProductIds());
        return orderEntity;
    }

    default OrderEntity convertCustom(OrderReadyEventDto eventDto, OrderEntity order){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.READY);
        orderEntity.setClientId(order.getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setProductIds(order.getProductIds());
        return orderEntity;
    }

    default OrderEntity convertCustom(OrderIssuedEventDto eventDto, OrderEntity order){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(eventDto.getOrderId());
        orderEntity.setEmployeeId(eventDto.getEmployeeId());
        orderEntity.setStatus(OrderStatus.ISSUED);

        orderEntity.setClientId(order.getClientId());
        orderEntity.setExpectedIssueTime(Instant.now());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setProductIds(order.getProductIds());
        return orderEntity;
    }

}
