package jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "이메일은 필수 입니다") // 값이 없으면 오류
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입니다") // 값이 없으면 오류
    private String password;

    private String salt; // 암호화 salt 저장
    private String roles;
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
