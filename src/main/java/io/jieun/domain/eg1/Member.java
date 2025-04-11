package io.jieun.domain.eg1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Entity
//@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    //domain 패키지가 entity패키지의 역할을 한다고 생각하삼

    @Id
    private String id;

    @Setter
    private String name;

    @Builder
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
