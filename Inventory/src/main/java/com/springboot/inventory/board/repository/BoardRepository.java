package com.springboot.inventory.board.repository;

import com.springboot.inventory.board.repository.search.BoardSearch;
import com.springboot.inventory.common.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    List<Board> findByIsNoticeTrue();
    List<Board> findByWriter(String username);

}
