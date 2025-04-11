package io.jieun.domain.eg3._1;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "players")
public class Player { //플레이어 입장에서는 다:일로 보임, 팀과 플레이어는 일대 다관계지만

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

//    @Column(name = "team_id")
//    private Integer teamId;

    //지연로딩을 하겠다.
    //@ManyToOne(fetch = FetchType.LAZY) //이 플레이어를 조회할떄, 다 갖고 오는게 아니라 프록시 객체를 넣어놓고
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id") //외래키 컬럼의 이름을 이렇게 지정해주겠다~
    private Team team;



}
