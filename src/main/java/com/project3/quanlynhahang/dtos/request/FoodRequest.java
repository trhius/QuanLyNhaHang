package com.project3.quanlynhahang.dtos.request;

import com.project3.quanlynhahang.enums.Category;
import com.project3.quanlynhahang.enums.FoodStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {
    private String name;
    private String description;
    private String ingredients;
    private String imageContent;
    private Category category;
    private Double price;
    private FoodStatus status;
}
