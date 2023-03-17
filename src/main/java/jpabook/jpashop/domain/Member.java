package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Email
    private String email;

    private String password;

    private String salt; // 암호화 salt 저장

    @Embedded
    private Address address;

    private String roles; // USER, ADMIN

    @JsonIgnore // json 데이터에서 주문정보 빼기
    @OneToMany(mappedBy = "member")  // 읽기 전용 값 넣는다고 FK 안변함
    private List<Order> orders = new ArrayList<>();

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
