package jpabook.jpashop.controller;

import jpabook.jpashop.config.SessionConst;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model){
        /**
         * Login 페이지로 이동
         **/
        if(member == null) {
            model.addAttribute("loginForm", new LoginForm());
            return "logins/loginForm";
        }
        /**
         * 메인 페이지로 이동
         **/
        model.addAttribute("member", member);
        return "home";
    }
}
