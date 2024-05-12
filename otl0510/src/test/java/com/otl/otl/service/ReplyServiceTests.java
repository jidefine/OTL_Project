package com.otl.otl.service;

import com.otl.otl.domain.Member;
import com.otl.otl.domain.Reply;
import com.otl.otl.dto.BoardDTO;
import com.otl.otl.dto.MemberDTO;
import com.otl.otl.repository.MemberRepository;
import com.otl.otl.repository.ReplyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.otl.otl.dto.ReplyDTO;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testRegister() {

        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyContent("ReplyDTO Text")
                .email("youjio2000@gmail.com")
                .bno(3L)
                .build();

        log.info(replyService.register(replyDTO));
    }

    @Test
    public void testRead(){

        // 테스트를 위한 댓글 번호 지정
        Long replyNo = 3L;

        // 댓글 조회
        ReplyDTO replyDTO = replyService.readOne(replyNo);
        Optional<Member> optionalMember = memberRepository.findByEmail(replyDTO.getEmail());

        MemberDTO memberDTO = new MemberDTO();
        optionalMember.ifPresent(member -> {
            // 회원 정보를 MemberDTO에 추가합니다.
            memberDTO.setNickname(member.getNickname());
            memberDTO.setMemberProfileImage(member.getMemberProfileImage());
        });

        // 댓글과 회원 정보를 합쳐서 로그에 출력합니다.
        log.info(replyDTO + memberDTO.toString());
    }
}
