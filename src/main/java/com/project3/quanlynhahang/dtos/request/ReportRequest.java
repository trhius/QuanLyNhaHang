package com.project3.quanlynhahang.dtos.request;

import com.project3.quanlynhahang.enums.ShippingStatus;
import com.project3.quanlynhahang.enums.TransactionStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequest {
    private Instant fromDate;
    private Instant toDate;
    private TransactionStatus transactionStatus;
    private ShippingStatus shippingStatus;
    private String searchByShipperName;
    private String searchByCustomerName;
}
