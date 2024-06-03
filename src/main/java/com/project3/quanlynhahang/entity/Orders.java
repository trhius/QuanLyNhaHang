package com.project3.quanlynhahang.entity;

import com.project3.quanlynhahang.enums.PaymentMethod;
import com.project3.quanlynhahang.enums.ShippingStatus;
import com.project3.quanlynhahang.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column
    private Long shipperId;

    @Column(name = "payment")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    @Column(name = "amount")
    private BigDecimal totalAmount;

    @Column
    private Double ratingPoint;

    @Column
    private String ratingComment;

    @Column
    private String remark;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column
    private Instant updatedAt;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

}
