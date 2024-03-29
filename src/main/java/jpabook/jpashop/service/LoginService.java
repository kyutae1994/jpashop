//package jpabook.jpashop.service;
//
//import jpabook.jpashop.domain.Member;
//import jpabook.jpashop.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Service
//@Transactional
//@RequiredArgsConstructor // final 있는 애만 생성자 생성해줌
//public class LoginService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public SignResponse login(SignRequest request) throws Exception {
//        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() ->
//                new BadCredentialsException("잘못된 계정정보입니다."));
//
//        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
//            throw new BadCredentialsException("잘못된 계정정보입니다.");
//        }
//
//        return SignResponse.builder()
//                .id(member.getId())
//                .name(member.getName())
//                .email(member.getEmail())
//                .roles(member.getRoles())
//                .token(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()))
//                .build();
//
//    }
//
//    public boolean register(SignRequest request) throws Exception {
//        try {
//            Member member = Member.builder()
//                    .password(passwordEncoder.encode(request.getPassword()))
//                    .name(request.getName())
//                    .email(request.getEmail())
//                    .build();
//
//            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
//
//            memberRepository.save(member);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new Exception("잘못된 요청입니다.");
//        }
//        return true;
//    }
//
//    public SignResponse getMember(String email) throws Exception {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
//        return new SignResponse(member);
//    }
//
//}
