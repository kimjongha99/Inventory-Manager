package com.springboot.inventory.board.repository;

import com.querydsl.core.Fetchable;
import com.springboot.inventory.board.repository.search.BoardSearch;
import com.springboot.inventory.common.entity.Board;
import com.springboot.inventory.common.enums.BoardType;
import com.springboot.inventory.common.enums.PostStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    List<Board> findAllByBoardTypeAndStatusOrderByBnoDesc(BoardType boardType, PostStatus status, Pageable pageable);

    List<Board> findByIsNoticeTrueAndBoardType(BoardType boardType);


    Optional<Board> findByBnoAndBoardType(Long bno, BoardType boardType);

}
