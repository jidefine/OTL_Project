package com.otl.otl.service;

import com.otl.otl.domain.Member;
import com.otl.otl.domain.Study;
import com.otl.otl.dto.MemberStudyDTO;
import com.otl.otl.repository.MemberRepository;
import com.otl.otl.repository.MemberStudyRepository;
import com.otl.otl.repository.StudyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
public class MemberStudyServiceTests {

    @Autowired
    private MemberStudyService memberStudyService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Test
    @Transactional
    public void testRequest() {

        // 현재 로그인한 회원의 이메일 주소 (예: youjio2000@gmail.com)
        String loggedInEmail = "youjio2000@gmail.com";
        Long sno = 3L;
        String comment = "참가신청 날아가요~~";

        // 로그인한 이메일 주소로 회원 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(loggedInEmail);
        // 회원이 존재할 경우에만 이메일 주소를 email 변수에 할당
        Member member = optionalMember.orElse(null);

        assertNotNull(member); // 회원이 없으면 테스트 실패

        // 스터디 번호로 스터디 조회
        Optional<Study> optionalStudy = studyRepository.findById(sno);
        Study study = optionalStudy.orElse(null);

        assertNotNull(study); // 스터디가 없으면 테스트 실패


        // MemberStudyDTO 생성 및 데이터 설정
        MemberStudyDTO memberStudyDTO = MemberStudyDTO.builder()
                .member(member) // 로그인한 회원
                .study(study) // 스터디 번호
                .isAccepted(false) // 참가 상태 초기값 설정
                .isManaged(false) // 방장 여부 초기값 설정
                .comment(comment) // 초기 댓글 설정
                .build();

        // 참가신청 등록
        Long msNo = memberStudyService.register(memberStudyDTO);
        assertNotNull(msNo);
    }

    @Test
    @Transactional
    public void testRequestRead(){
        Long sno = 3L; // 테스트용 스터디 번호

        // 참가신청 조회
        List<MemberStudyDTO> waitingParticipants = memberStudyService.findWaitingParticipant(sno, false, false);

        // 조회된 참가신청이 비어있지 않은지 확인
        assertFalse(waitingParticipants.isEmpty());

        // 참가자 목록 출력
        log.info("참가 신청 대기중인 참가자 목록:");
        for (MemberStudyDTO participant : waitingParticipants) {
            log.info("참가자 이메일: {}", participant.getMember().getEmail());
            //log.info("참가자 닉네임: {}", participant.getNickname());
            //log.info("참가자 프로필 이미지: {}", participant.getMemberProfileImage());
            //log.info("참가자 카테고리: {}", participant.getInterests());
            log.info("참가신청 댓글: {}", participant.getComment());
            log.info("---------------------------------------------");
        }
    }
}