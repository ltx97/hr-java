package com.forme.alan.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUser {
    @NotBlank
    private String mobile;
    private String code;
}
