package jpabook.jpashop.controller;

import jpabook.jpashop.config.SessionConst;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model){
        /**
         * Login 페이지로 이동
         **/
        if(member == null) {
            return "logins/loginForm";
        }
        /**
         * 메인 페이지로 이동
         **/
        model.addAttribute("member", member);
        return "home";
    }

    // 로그인 페이지
    @GetMapping("/home")
    public String login() {
        return "home";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
//        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
