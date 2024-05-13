package com.otl.otl.service;

import com.otl.otl.domain.Board;
import com.otl.otl.dto.BoardDTO;
import com.otl.otl.domain.Member;
import com.otl.otl.domain.Reply;
import com.otl.otl.dto.ReplyDTO;
import com.otl.otl.repository.BoardRepository;
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

    private final BoardRepository boardRepository;
    private  final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    @Override
    public Long register(ReplyDTO replyDTO) {
        // 회원 이메일로 Member 엔티티를 조회합니다.
        Optional<Member> optionalMember = memberRepository.findByEmail(replyDTO.getEmail());
        Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("해당 이메일의 회원이 존재하지 않습니다: " + replyDTO.getEmail()));

        // 게시글 번호로 Board 엔티티를 조회합니다.
        Optional<Board> optionalBoard = boardRepository.findById(replyDTO.getBno());
        Board board = optionalBoard.orElseThrow(() -> new IllegalArgumentException("해당 번호의 게시글이 존재하지 않습니다: " + replyDTO.getBno()));

        // ReplyDTO를 Reply 엔티티로 변환
        Reply reply = Reply.builder()
                .replyContent(replyDTO.getReplyContent())
                .board(board)
                .member(member)
                //.isDeleted(false)
                .build();

        // 생성된 Board 객체를 저장하고, 저장된 게시글의 ID를 반환합니다.
        Reply savedReply = replyRepository.save(reply);
        log.info("새로운 게시글이 등록되었습니다: 게시글 ID {}", savedReply.getReplyNo());
        return savedReply.getReplyNo();
    }

     @Override
    public ReplyDTO readOne(Long replyNo) {
        Optional<Reply> result = replyRepository.findById(replyNo);

        Reply reply = result.orElseThrow();

        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);

        return replyDTO;
    }

    @Override
    public void remove(Long replyNo) {
        Optional<Reply> result = replyRepository.findById(replyNo);
        result.ifPresent(reply -> replyRepository.delete(reply)); // 삭제 처리
    }
}
