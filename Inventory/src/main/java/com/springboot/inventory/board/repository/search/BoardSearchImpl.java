package com.springboot.inventory.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.springboot.inventory.common.entity.Board;
import com.springboot.inventory.common.entity.QBoard;
import com.springboot.inventory.common.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(BoardType boardType, Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(board.title.contains("11")); // title like ...

        booleanBuilder.or(board.content.contains("11")); // content like ....

        query.where(booleanBuilder);

        query.where(board.bno.gt(0L));

        // Add condition for board type.
        query.where(board.boardType.eq(boardType));



        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();


        return new PageImpl<>(list, pageable, count);

    }

    @Override
    public Page<Board> searchAll(BoardType boardType, String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        //bno > 0
        query.where(board.bno.gt(0L));

        // Add condition for board type.
        query.where(board.boardType.eq(boardType));


        this.getQuerydsl().applyPagination(pageable, query);


        List<Board> list = query.fetch();

        long count =query.fetchCount();

        return new PageImpl<>(list, pageable,count);

    }
}
