package com.project3.quanlynhahang.entity;

import com.project3.quanlynhahang.enums.Category;
import com.project3.quanlynhahang.enums.FoodStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "food")
@SQLRestriction(value = "is_deleted = false")
public class Food  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String ingredients;

    @Column
    private String image;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private Double price;

    @Column
    @Enumerated(EnumType.STRING)
    private FoodStatus status;

    @Column(name = "is_deleted")
    private boolean deleted = false;

}
