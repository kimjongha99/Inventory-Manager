package com.springboot.inventory.board.repository;

import com.springboot.inventory.board.repository.search.BoardSearch;
import com.springboot.inventory.common.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {


}
