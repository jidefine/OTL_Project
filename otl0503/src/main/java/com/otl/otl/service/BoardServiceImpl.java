package com.otl.otl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.otl.otl.domain.Board;
import com.otl.otl.dto.BoardDTO;

@Service
@Log4j2
@RequiredArgsConstructor
//@Transactional
public class BoardServiceImpl implements BoardService{
    private final ModelMapper modelMapper;
    //private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);

        //Long board_id = boardRepository.save(board).getBno();
        //return board_id;
        return null;
    }
}
