package com.otl.otl.controller;


import com.otl.otl.domain.Board;
import com.otl.otl.domain.Member;
import com.otl.otl.dto.BoardDTO;
import com.otl.otl.dto.MemberDTO;
import com.otl.otl.repository.MemberRepository;
import com.otl.otl.service.BoardService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated // DTO 유효성 검사를 위한 어노테이션
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    // POST 요청을 처리하는 메소드, 게시글 저장
    @PostMapping("/saveBoard")
    public ResponseEntity<String> saveBoard(@Valid @RequestBody BoardDTO boardDTO) {
        // BoardService를 사용하여 게시글을 저장하고, 성공 여부를 확인합니다.
        // 게시글 저장 시도 로깅
        log.info("게시글 저장 시도 : {}", boardDTO);

        Long bno = boardService.register(boardDTO);

        // 저장 결과에 따라 적절한 응답을 반환합니다.
        if (bno != null) {
            log.info("게시글이 성공적으로 저장되었습니다. 게시글 ID: {}", bno);
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 등록되었습니다.");
        } else {
            log.info("게시글 저장 실패: {}", boardDTO);
            return ResponseEntity.badRequest().body("게시글 등록에 실패했습니다.");
        }
    }

    @PostMapping("/readBoard")
    public ResponseEntity<String> readBoard(@Valid @RequestBody Long bno) {
        // 게시글 조회 시도 로깅
        log.info("게시글 조회 시도 : {}", bno);

        BoardDTO boardDTO = boardService.readOne(bno);

        // 저장 결과에 따라 적절한 응답을 반환합니다.
        if (boardDTO != null) {
            // 이메일 주소로 회원 조회
            Optional<Member> optionalMember = memberRepository.findByEmail(boardDTO.getEmail());

            MemberDTO memberDTO = new MemberDTO();
            optionalMember.ifPresent(member -> {
                // 회원 정보 가져와서 MemberDTO에 추가
                memberDTO.setNickname(member.getNickname());
                memberDTO.setMemberProfileImage(member.getMemberProfileImage());
            });

            // boardDTO와 memberDTO의 정보를 문자열로 조합하여 반환
            String response = "BoardDTO: " + boardDTO.toString() + ", MemberDTO: " + memberDTO.toString();

            // boardDTO와 memberDTO의 정보를 조합하여 응답
            //BoardAndMemberResponse response = new BoardAndMemberResponse(boardDTO, memberDTO);

            log.info("게시글이 성공적으로 조회되었습니다. 게시글 ID: {}", bno);
            return ResponseEntity.ok().body(response);
        } else {
            log.info("게시글 조회 실패: {}", bno);
            return ResponseEntity.badRequest().body("게시글 조회에 실패했습니다.");
        }
    }

}
