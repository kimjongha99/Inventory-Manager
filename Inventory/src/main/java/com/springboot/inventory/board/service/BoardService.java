package com.springboot.inventory.board.service;


import com.springboot.inventory.board.dto.BoardDTO;
import com.springboot.inventory.board.dto.BoardPreviewDTO;
import com.springboot.inventory.board.dto.PageRequestDTO;
import com.springboot.inventory.board.dto.PageResponseDTO;
import com.springboot.inventory.common.enums.BoardType;
import com.springboot.inventory.common.enums.PostStatus;

import java.util.List;

public interface BoardService {


    // Add BoardType parameter to register method.
    Long register(BoardType boardType, BoardDTO boardDTO);

    // Add BoardType parameter to readOne method.
    BoardDTO readOne(BoardType boardType, Long bno);

    // Add BoardType parameter to modify method.
    void modify(BoardType boardType, BoardDTO boardDTO);

    // Add BoardType parameter to remove method.
    void remove(BoardType boardType, Long bno);

    // Add BoardType parameter to list method.
    PageResponseDTO<BoardDTO> list(BoardType boardtype, PageRequestDTO pageRequestDTO);


    void changeStatus(BoardType boardType, Long bno, PostStatus status);

    List<BoardDTO> listNotices(BoardType boardType);

    List<BoardPreviewDTO> getTop10BoardsPurchase();
    List<BoardPreviewDTO> getTop10BoardsRepair();

}
