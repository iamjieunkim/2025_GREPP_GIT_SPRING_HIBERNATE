package io.jieun.domain.eg2._3;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import io.jieun.domain.eg2._3.Level;

@Getter
@Entity
public class GymMemberShip{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
//    @Enumerated(EnumType.ORDINAL)
    @Enumerated(EnumType.STRING)
    private Level membershipLevel;


}
