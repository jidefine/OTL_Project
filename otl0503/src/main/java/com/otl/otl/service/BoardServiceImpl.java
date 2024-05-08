package com.otl.otl.service;

import com.otl.otl.repository.BoardRepository;
import com.otl.otl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.otl.otl.domain.Board;
import com.otl.otl.domain.Member;
import com.otl.otl.dto.BoardDTO;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);

        int board_id = boardRepository.save(board).getBoard_id();
        return (long) board_id;
    }

    @Override
    public BoardDTO readOne(Long board_id) {
        Optional<Board> result = boardRepository.findById(board_id);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById((long) boardDTO.getBoard_id());

        Board board = result.orElseThrow();

        board.change(boardDTO.getBoard_title(), boardDTO.getBoard_content());
    }

    @Override
    public void remove(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById((long) boardDTO.getBoard_id());

        Board board = result.orElseThrow();

        board.delete(); // 삭제 처리
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("board_id");

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board,BoardDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
