package jpabook.jpashop.controller;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailForm {
    @Email
    @NotBlank(message = "이메일(필수)")
    private String email;
}
