package io.jieun.domain.eg2._1;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class Post {

    //ddl-auto

    @Id
    private Integer id;

    @Column(length = 100, nullable = false) //varchar(100)으로 됨
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
