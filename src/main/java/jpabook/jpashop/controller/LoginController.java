package jpabook.jpashop.controller;

import jpabook.jpashop.dto.LoginForm;
import jpabook.jpashop.service.LoginService;
import jpabook.jpashop.config.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public TokenInfo login(LoginForm form) {

        String email = form.getEmail();
        String password = form.getPassword();
        TokenInfo tokenInfo = loginService.login(email, password);
        return tokenInfo;
    }

    // 서블릿 HTTP 세션 사용
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        // getSession(false) 를 사용해야 함 (세션이 없더라도 새로 생성하면 안되기 때문)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
