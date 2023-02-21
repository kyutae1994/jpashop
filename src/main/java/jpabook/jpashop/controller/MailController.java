package jpabook.jpashop.controller;

import jpabook.jpashop.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    //이메일 인증
    @GetMapping("/members/createMemberForm")
    @ResponseBody
    public String mailCheck(String email) throws MessagingException, UnsupportedEncodingException {
        System.out.println("이메일 인증 요청이 들어옴!");
        System.out.println("이메일 인증 이메일 : " + email);
        return mailService.sendEmail(email);
    }
}
