package com.otl.otl.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.otl.otl.domain.Board;
import com.otl.otl.domain.Member;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        // 1이상 ~ 10이하까지
        IntStream.rangeClosed(1, 10).forEach(i -> {
            // 회원 정보 조회
            Optional<Member> optionalMember = memberRepository.findByEmail("example1@example.com"); // 회원 이메일 주소로 회원 조회

            // Optional 객체 안전하게 처리
            Member member = optionalMember.orElse(null);

            // 회원이 존재하는 경우에만 게시글 생성
            if (member != null) {
                // 게시글 생성
                Board board = Board.builder()
                        .board_title("title..." + i)
                        .board_content("content..." + i)
                        .member(member) // 조회한 회원 정보 할당
                        .build();

                // 게시글 저장
                Board result = boardRepository.save(board);
                log.info("board_id: " + result.getBoard_id());
            } else {
                log.warn("회원 정보를 찾을 수 없습니다.");
            }
        });
    }

    @Test
    public void testSelect() {
        int board_id = 10;

        Long bno = Long.valueOf(board_id); // int를 Long으로 변환

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);

    }

    @Test
    public void testUpdate() {

        int board_id = 9;

        Long bno = Long.valueOf(board_id); // int를 Long으로 변환

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update..title 9", "update 되나요?");

        boardRepository.save(board);

    }

    @Test
    public void testDelete() {
        int board_id = 10;

        Long bno = Long.valueOf(board_id); // int를 Long으로 변환

        boardRepository.deleteById(bno);
    }

}
