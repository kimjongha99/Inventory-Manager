package com.springboot.inventory.board.service;


import com.springboot.inventory.board.dto.PageRequestDTO;
import com.springboot.inventory.board.dto.PageResponseDTO;
import com.springboot.inventory.board.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);


    int countRepliesByBoardId(Long boardId);
}
