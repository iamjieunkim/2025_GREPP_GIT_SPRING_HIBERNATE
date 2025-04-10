package io.jieun.domain.eg1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    //domain 패키지가 entity패키지의 역할을 한다고 생각하삼

    @Id
    private Long id;

    @Setter
    private String name;

    @Builder
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
