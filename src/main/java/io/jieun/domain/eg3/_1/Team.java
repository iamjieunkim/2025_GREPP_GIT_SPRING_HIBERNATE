package io.jieun.domain.eg3._1;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY) //relationship반대 방향에 붙여줌
    private List<Player> players;

}
