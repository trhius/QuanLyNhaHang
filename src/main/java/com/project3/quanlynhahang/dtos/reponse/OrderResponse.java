package com.project3.quanlynhahang.dtos.reponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project3.quanlynhahang.enums.ShippingStatus;
import com.project3.quanlynhahang.enums.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private List<OrderItemResponse> orderItems;
    private BigDecimal total;
    private String shipperName;
    private TransactionStatus transactionStatus;
    private ShippingStatus shippingStatus;
    private Double ratingPoint;
    private String ratingComment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant updatedAt;

}
