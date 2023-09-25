package com.springboot.inventory.board.repository.search;


import com.springboot.inventory.common.entity.Board;
import com.springboot.inventory.common.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    // Add BoardType parameter to search1 method.
    Page<Board> search1(BoardType boardType, Pageable pageable);

    // Add BoardType parameter to searchAll method.
    Page<Board> searchAll(BoardType boardType, String[] types, String keyword, Pageable pageable);
}
