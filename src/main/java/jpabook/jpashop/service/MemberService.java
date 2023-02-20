package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 있는 애만 생성자 생성해줌
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // @Autowired  생성자 하나만 있으면 default로 자동으로 Autowired가 됨

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        Optional<Member> email = memberRepository.findByEmail(member.getEmail());
        if (email.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 한건 조회
     */
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        if (member.getName().equals(name)) { // 중복 회원 검증
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
        member.setName(name);
    }
}
