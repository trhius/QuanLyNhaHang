package com.project3.quanlynhahang.entity;

import com.project3.quanlynhahang.enums.AccountStatus;
import com.project3.quanlynhahang.enums.Gender;
import com.project3.quanlynhahang.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private LocalDate dateOfBirth;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

}
