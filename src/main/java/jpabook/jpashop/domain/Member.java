package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

//    @JsonIgnore // json 데이터에서 주문정보 빼기
    @OneToMany(mappedBy = "member")  // 읽기 전용 값 넣는다고 FK 안변함
    private List<Order> orders = new ArrayList<>();
}
