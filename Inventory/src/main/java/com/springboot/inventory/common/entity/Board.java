package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.BoardType;
import com.springboot.inventory.common.enums.PostStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends Timestamped{
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

    @Column(nullable = false) // 공지사항 여부. 필요에 따라서 null 허용 여부를 변경하세요.
    private Boolean isNotice ;

    // Add board type.
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

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

    public void setIsNotice(Boolean isNotice){
        this.isNotice=isNotice;
    }


    // Add setter for the new field.
    public void setBoardType(BoardType boardType){
        this.boardType=boardType;
    }
}
