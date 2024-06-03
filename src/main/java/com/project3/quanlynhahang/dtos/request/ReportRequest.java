package com.project3.quanlynhahang.dtos.request;

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
}
