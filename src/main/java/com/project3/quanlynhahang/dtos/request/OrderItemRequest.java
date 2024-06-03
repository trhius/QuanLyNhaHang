package com.project3.quanlynhahang.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemRequest {
    private Long foodId;
    private Integer quantity;
    private String note;
}
