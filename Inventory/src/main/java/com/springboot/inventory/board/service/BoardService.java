package com.springboot.inventory.board.service;


import com.springboot.inventory.board.dto.BoardDTO;
import com.springboot.inventory.board.dto.PageRequestDTO;
import com.springboot.inventory.board.dto.PageResponseDTO;
import com.springboot.inventory.common.enums.PostStatus;

import java.util.List;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);



    void changeStatus(Long bno, PostStatus status);

    List<BoardDTO> listNotices();
}
