package com.otl.otl.service;

import com.otl.otl.domain.Board;
import com.otl.otl.dto.BoardDTO;
import com.otl.otl.domain.Member;
import com.otl.otl.domain.Reply;
import com.otl.otl.dto.ReplyDTO;
import com.otl.otl.repository.MemberRepository;
import com.otl.otl.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    private final BoardDTO boardDTO;

    private  final MemberRepository memberRepository;

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;
    @Override
    public Long register(ReplyDTO replyDTO) {
        Optional<Member> optionalMember = memberRepository.findByEmail(boardDTO.getEmail());
        Member member = optionalMember.orElse(null); // Optional이 비어있으면 null을 반환
        if (member == null) {
            // 해당 이메일로 등록된 회원이 없으면 게시글 등록에 실패합니다.
            log.error("회원 등록 실패: 이메일 {} 에 해당하는 회원이 없습니다.", boardDTO.getEmail());
            return null;
        }

        // BoardDTO를 Board 엔티티로 변환
        Board board = Board.builder()
                .boardTitle(boardDTO.getBoardTitle())
                .boardContent(boardDTO.getBoardContent())
                .member(member)
                .isDeleted(false) // 초기값 설정
                .build();

        // 생성된 Board 객체를 저장하고, 저장된 게시글의 ID를 반환합니다.
        Board savedBoard = boardRepository.save(board);
        log.info("새로운 게시글이 등록되었습니다: 게시글 ID {}", savedBoard.getBno());
        return savedBoard.getBno();
        return null;
    }

    @Override
    public ReplyDTO readOne(Long bno) {
        return null;
    }

    @Override
    public Reply modify(ReplyDTO replyDTO) {
        return null;
    }

    @Override
    public void remove(Long bno) {

    }

    @Override
    public Page<ReplyDTO> findReplys(int page, int size) {
        return null;
    }
}
