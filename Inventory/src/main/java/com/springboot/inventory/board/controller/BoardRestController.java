package com.springboot.inventory.board.controller;


import com.springboot.inventory.board.dto.BoardPreviewDTO;
import com.springboot.inventory.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService boardService;


    @GetMapping("/boards/purchase/top10")
    public List<BoardPreviewDTO> getTop10BoardsPurchase() {
        return boardService.getTop10BoardsPurchase();
    }

    @GetMapping("/boards/repair/top10")
    public List<BoardPreviewDTO> getTop10BoardsRepair() {
        return boardService.getTop10BoardsRepair();
    }
}