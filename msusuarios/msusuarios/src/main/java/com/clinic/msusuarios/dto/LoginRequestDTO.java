package com.clinic.msusuarios.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
}