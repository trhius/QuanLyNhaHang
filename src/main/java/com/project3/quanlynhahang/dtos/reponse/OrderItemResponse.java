package com.project3.quanlynhahang.dtos.reponse;

import com.project3.quanlynhahang.enums.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private String foodName;
    private Category category;
    private String description;
    private String ingredient;
    private Double price;

}
