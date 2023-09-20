package com.springboot.inventory.request.board.service;


import com.springboot.inventory.request.board.dto.BoardDTO;
import com.springboot.inventory.request.board.dto.PageRequestDTO;
import com.springboot.inventory.request.board.dto.PageResponseDTO;
import com.springboot.inventory.request.board.entity.PostStatus;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);



    void changeStatus(Long bno, PostStatus status);
}
