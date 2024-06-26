package com.project3.quanlynhahang.dtos.reponse;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private Integer totalOrders;
    private BigDecimal totalAmount;
    private List<OrderResponse> orderResponses;

}
