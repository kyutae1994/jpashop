package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // @PersistenceContext <-- spring data jpa가 자동으로 해줌
    // EntityManager는 PersistenceContext가 있어야 자동으로 주입해줌
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 영속성 콘테스트
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // jpa 쿼리는 약간 다름
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList().stream().findAny();
    }
}
