package com.otl.otl.repository.search;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.otl.otl.domain.Board;
//import com.otl.otl.domain.QBoard;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
//        QBoard board = QBoard.board;
//        JPQLQuery<Board> query = from(board);
//        query.where(board.board_title.contains("1"));

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        return null;
    }
}
