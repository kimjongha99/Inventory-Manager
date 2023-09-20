package com.springboot.inventory.request.board.entity;

import com.springboot.inventory.common.entity.TimeStamp;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 500, nullable = false) //컬럼의 길이와 null허용여부
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void changeStatus(PostStatus status) {
        this.status=status;
    }



    public void setTitle(String title) {
    this.title =title;
    }

    public void setContent(String content) {
        this.content = content;

    }

    public void setWriter(String writer) {
        this.writer=writer;
    }
}
