package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.LoginService;
import jpabook.jpashop.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Valid LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

        // LoginForm 에 email 혹은 password 의 값이 존재하지 않을 때
        if (bindingResult.hasErrors()) {
            return "/logins/loginForm";
        }

        Member loginMember = loginService.login(form.getEmail(), form.getPassword());

        /**
         * 로그인 성공 처리
         **/
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
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
