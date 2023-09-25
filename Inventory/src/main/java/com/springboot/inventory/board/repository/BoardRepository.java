package com.springboot.inventory.board.repository;

import com.springboot.inventory.board.repository.search.BoardSearch;
import com.springboot.inventory.common.entity.Board;
import com.springboot.inventory.common.enums.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {


    List<Board> findByIsNoticeTrueAndBoardType(BoardType boardType);

    // Refactor to find by writer and board type.
    List<Board> findByWriterAndBoardType(String username, BoardType boardType);

    Optional<Board> findByBnoAndBoardType(Long bno, BoardType boardType);

    List<Board> findByIsNoticeTrue();
}
