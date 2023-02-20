package jpabook.jpashop.controller;

import jpabook.jpashop.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/logins/mailConfirm")
    public String mailConfirm(@RequestBody MailForm form) throws MessagingException, UnsupportedEncodingException {

        String authCode = mailService.sendEmail(form.getEmail());
        return authCode;
    }
}
