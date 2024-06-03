package com.project3.quanlynhahang.dtos.request;

import com.project3.quanlynhahang.enums.AccountStatus;
import com.project3.quanlynhahang.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInfoRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private AccountStatus status;
}
