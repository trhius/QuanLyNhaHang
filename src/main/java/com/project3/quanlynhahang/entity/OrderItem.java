package com.project3.quanlynhahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id", nullable = false)
    private Food food;

    @Column
    private Integer quantity;

}
