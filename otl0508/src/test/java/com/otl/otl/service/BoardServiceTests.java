package com.otl.otl.service;

import com.otl.otl.domain.Member;
import com.otl.otl.dto.BoardDTO;
import com.otl.otl.dto.MemberDTO;
import com.otl.otl.repository.BoardRepository;
import com.otl.otl.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testRegister() {

        //log.info(boardService.getClass().getName());

        // 이메일 주소로 회원 조회
        Optional<Member> optionalMember = memberRepository.findByEmail("youjio2000@gmail.com");

        // member 테이블에 없는 이메일 주소로는 게시글 저장 안됨
        //Optional<Member> optionalMember = memberRepository.findByEmail("test@gmail.com");

        // 회원이 존재할 경우에만 이메일 주소를 email 변수에 할당
        String email = optionalMember.map(Member::getEmail).orElse(null);

        // BoardDTO 생성 및 데이터 설정
        BoardDTO boardDTO = BoardDTO.builder()
                .boardTitle("Sample Title...1")
                .boardContent("Sample Content...1")
                .email(email) // 회원 객체 할당
                .build();

        // 게시글 저장
        Long bno = boardService.register(boardDTO);
        log.info("bno: " + bno);
    }

    @Test
    public void testRead(){

        // 게시글 조회
        BoardDTO boardDTO = boardService.readOne(2L);

        // 이메일 주소로 회원 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(boardDTO.getEmail());

        MemberDTO memberDTO = new MemberDTO();
        optionalMember.ifPresent(member -> {
            // 회원 정보 가져와서 MemberDTO에 추가
            memberDTO.setNickname(member.getNickname());
            memberDTO.setMemberProfileImage(member.getMemberProfileImage());
        });

        // 게시글과 회원 정보를 합쳐서 로그에 출력
        log.info(boardDTO.toString() + " " + memberDTO.toString());
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .boardTitle("Updated....2")
                .boardContent("Updated content 2...")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testDelete(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(3L)
                .build();

        boardService.remove(boardDTO);
    }


}
