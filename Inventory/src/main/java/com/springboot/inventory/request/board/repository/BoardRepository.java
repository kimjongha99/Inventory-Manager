package com.springboot.inventory.request.board.repository;

import com.springboot.inventory.request.board.entity.Board;
import com.springboot.inventory.request.board.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {


}
