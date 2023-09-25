package com.springboot.inventory.board.dto;

import com.springboot.inventory.common.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPreviewDTO {
    private String title;
    private String writer;

    // Add board type.
    private BoardType boardType;
    public BoardPreviewDTO(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }

    // Add getter and setter for the new field.
    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
}
