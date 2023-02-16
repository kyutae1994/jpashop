package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.NotCorrespondingEmailException;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 있는 애만 생성자 생성해줌
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String email, String password) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (!findMember.orElseThrow(() -> new NotCorrespondingEmailException("해당 이메일이 존재하지 않습니다.")).checkPassword(password)) {
            throw new IllegalStateException("이메일과 비밀번호가 일치하지 않습니다.");
        }
        return findMember.get();
    }

    public List<Member> findBySalt(String email) {
        return memberRepository.findBySalt(email);
    }
}
