package com.project3.quanlynhahang.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {
    private Long customerId;
    private Double ratingPoint;
    private String ratingComment;
}
