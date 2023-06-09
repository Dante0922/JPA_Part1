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

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    // colletion은 필드에서 바로 초기화하는 것이 안전하다.
    // 하이버네이트로 감쌀 경우 에러가 객체가 감싸지기 때문
    private List<Order> orders = new ArrayList<>();


}
