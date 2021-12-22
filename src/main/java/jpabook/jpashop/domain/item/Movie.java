package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") // 한곳에 다 때려박아서 구분
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
