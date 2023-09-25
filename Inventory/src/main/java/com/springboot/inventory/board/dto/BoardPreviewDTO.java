package com.springboot.inventory.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardPreviewDTO {
    private String title;
    private String writer;


    public BoardPreviewDTO(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }
}
