package com.springboot.inventory.board.dto;

import com.springboot.inventory.common.enums.BoardType;
import com.springboot.inventory.common.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String writer;

    private PostStatus status; // 게시물 상태


    private boolean isNotice; // 공지사항 여부


    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Long replyCount;


    // Add board type.
    private BoardType boardType;

    public Boolean getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Boolean isNotice) {
        this.isNotice = Optional.ofNullable(isNotice).orElse(false);
    }
    // Add getter and setter for the new field.
    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
