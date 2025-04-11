package io.jieun.domain.eg3._2;

import jakarta.persistence.*;

@Entity
public class Locker {

    @Id
    @Column(name = "locker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    @OneToOne(mappedBy = "locker")
    private LockerOwner owner;
}
