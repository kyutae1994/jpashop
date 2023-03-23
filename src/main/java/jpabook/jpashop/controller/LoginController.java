package jpabook.jpashop.controller;

import jpabook.jpashop.dto.LoginForm;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse res) {
        // 로그인 성공시 쿠키에 token 저장
        System.out.println(loginForm);
        return "logins/loginForm";
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

    @GetMapping("/user")
    public String user() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "home";
    }
}
