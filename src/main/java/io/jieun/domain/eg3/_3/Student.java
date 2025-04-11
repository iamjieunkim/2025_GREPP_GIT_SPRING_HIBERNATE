package io.jieun.domain.eg3._3;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable( //student를 주인으로 지정 해줌
            name = "STUDENT_LECTURE_RELATION",
            joinColumns = @JoinColumn(name = "STUDENT_ID"), //내가 가지고 있어야 할 외래키를 이곳에 지정
            inverseJoinColumns = @JoinColumn(name = "LECTURE_ID")
    )
    private List<Lecture> lectureList;
}
