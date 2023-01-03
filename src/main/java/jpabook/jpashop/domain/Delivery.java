package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY) // 읽기 전용 값 넣는다고 FK 안변함
    private Order order;

    @Embedded
    private Address address;
                                  // 거의 STRING 씀
    @Enumerated(EnumType.STRING)  // ORDINAL 숫자로 들어감
    private DeliveryStatus status; // READY, COMP
}
