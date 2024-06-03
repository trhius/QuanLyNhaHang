package com.project3.quanlynhahang.dtos.request;

import com.project3.quanlynhahang.enums.Gender;
import com.project3.quanlynhahang.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Role role;
}
