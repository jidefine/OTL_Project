package com.otl.otl.service;

import com.otl.otl.dto.BoardDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    BoardDTO readOne(Long board_id); // 조회
    void modify(BoardDTO boardDTO); // 수정
}
