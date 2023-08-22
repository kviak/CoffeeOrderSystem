package ru.kviak.coffeeorder.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonTypeName(value = "regist")
public class OrderRegisteredEventDto extends OrderEventDto{

    private long employeeId;
    private long clientId;
    private long productId;
    private long price;


//    @Override
//    public Long getOrderId() {
//        return null;
//    }

    @Override
    public long getEmployeeId(){
        return employeeId;
    }
}
