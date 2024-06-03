package com.project3.quanlynhahang.dtos.request;

import com.project3.quanlynhahang.enums.PaymentMethod;
import com.project3.quanlynhahang.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long customerId;
    private List<OrderItemRequest> orderItems;
    private PaymentMethod paymentMethod;
    private TransactionStatus transactionStatus;
    private String note;

}
