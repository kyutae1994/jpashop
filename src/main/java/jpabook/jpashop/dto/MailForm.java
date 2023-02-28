package jpabook.jpashop.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class MailForm {

    @Email
    public String email;
}
