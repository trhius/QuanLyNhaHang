package com.project3.quanlynhahang.dtos.reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String message;
    private Object object;
    // TODO
    private String token;
}
