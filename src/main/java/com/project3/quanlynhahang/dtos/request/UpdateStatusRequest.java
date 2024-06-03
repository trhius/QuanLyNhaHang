package com.project3.quanlynhahang.dtos.request;

import com.project3.quanlynhahang.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {
    private Long customerId;
    private AccountStatus status;
}
