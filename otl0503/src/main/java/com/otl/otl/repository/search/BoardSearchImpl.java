package com.otl.otl.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.otl.otl.domain.Board;
import com.otl.otl.domain.QBoard;
import com.otl.otl.domain.QMember;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){

        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        query.where(board.board_title.contains("1"));
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QMember member = QMember.member; // Member 엔티티의 Q 타입 추가

        JPQLQuery<Board> query = from(board);

        if((types != null && types.length > 0) && keyword != null){
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types){
                switch(type){
                    case "t":
                        booleanBuilder.or(board.board_title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.board_content.contains(keyword));
                        break;
                    case "w":
                        // Member 엔티티의 필드를 기준으로 검색
                        booleanBuilder.or(board.member.email.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        // AND bno > 0L
        query.where(board.board_id.gt(0L));

        // ORDER BY bno DESC limit 1, 10;
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

}
