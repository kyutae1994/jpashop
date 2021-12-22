package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 한곳에 다 때려박아서 구분
@Getter
@Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
