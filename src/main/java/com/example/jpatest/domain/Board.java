package com.example.jpatest.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor
public class Board {

    @Id
    @Column(name = "board_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "regdate", nullable = false)
    private Date regDate;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "writer", referencedColumnName = "user_id")
    private User writer;

}
