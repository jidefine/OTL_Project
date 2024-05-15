package com.otl.otl.service;

import com.otl.otl.domain.*;
import com.otl.otl.dto.MemberDTO;
import com.otl.otl.dto.MemberStudyDTO;
import com.otl.otl.dto.MemberStudyProjection.MemberStudyProjectionImpl;
import com.otl.otl.dto.StudyDTO;
import com.otl.otl.dto.StudyListDTO;
import com.otl.otl.repository.BoardRepository;
import com.otl.otl.repository.MemberRepository;
import com.otl.otl.repository.MemberStudyRepository;
import com.otl.otl.repository.StudyRepository;
import com.otl.otl.service.converter.CustomConverters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberStudyServiceImpl implements MemberStudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final MemberStudyRepository memberStudyRepository;

    /*  WHERE sno = ? AND is_accpeted = 0 AND is_managed = 1 ;
       - 방장 페이지 -> 참가 대기 멤버 조회 (GET)
    */


    @Override
    public List<MemberStudyDTO> findWaitingParticipant(Long sno, Boolean isAccepted, Boolean isManaged) {
        List<MemberStudy> memberStudies = memberStudyRepository.findMemberBySnoAndAcceptedFalse(sno, isAccepted, isManaged);

        // 조회된 MemberStudy 엔티티를 처리하여 DTO로 변환
        List<MemberStudyDTO> memberStudyDTOs = new ArrayList<>();
        for (MemberStudy memberStudy : memberStudies) {
            // 이메일로 회원 조회
            Optional<Member> optionalMember = memberRepository.findByEmail(memberStudy.getMember().getEmail());
            Member member = optionalMember.orElse(null);

            if (member != null) {
                Study study = memberStudy.getStudy(); // 해당 스터디 가져오기

                // DTO 객체 생성 및 데이터 설정
                MemberStudyDTO memberStudyDTO = MemberStudyDTO.builder()
                        .msNo(memberStudy.getMsNo())
                        .member(member)       // 스터디 참가 멤버
                        .study(study)        // 해당 스터디
                        .isAccepted(memberStudy.isAccepted())    // 참가 상태
                        .isManaged(memberStudy.isManaged())    // 방장 여부
                        .comment(memberStudy.getComment())  // 참가신청 댓글 설정
                        .build();
                memberStudyDTOs.add(memberStudyDTO); // DTO 객체를 리스트에 추가
            }
        }

        return memberStudyDTOs; // DTO 리스트 반환
    }

    @Override
    public Long register(MemberStudyDTO memberStudyDTO) {
        // 회원 조회
        Optional<Member> optionalMember = memberRepository.findByEmail(memberStudyDTO.getMember().getEmail());
        Member member = optionalMember.orElse(null);
        if (member == null) {
            log.error("참가신청 실패: 이메일 {} 에 해당하는 회원이 없습니다.", memberStudyDTO.getMember().getEmail());
            return null;
        }

        // 스터디 조회
        Optional<Study> optionalStudy = studyRepository.findById(memberStudyDTO.getStudy().getSno());
        Study study = optionalStudy.orElse(null);
        if (study == null) {
            log.error("참가신청 실패: 스터디 번호 {} 에 해당하는 스터디가 없습니다.", memberStudyDTO.getStudy().getSno());
            return null;
        }

        // MemberStudyDTO를 MemberStudy 엔티티로 변환
        MemberStudy memberStudy = MemberStudy.builder()
                .member(member)
                .study(study)
                .isAccepted(false)
                .isManaged(false)
                .comment(memberStudyDTO.getComment()) // DTO에서 comment 가져오기
                .build();

        // 생성된 MemberStudy 객체 저장
        MemberStudy savedRequest = memberStudyRepository.save(memberStudy);
        log.info("참가신청이 완료되었습니다: 참가신청 ID {}", savedRequest.getMsNo());
        return savedRequest.getMsNo();
    }

    @Override
    public MemberStudyDTO readOne(Long msNo) {
        return null;
    }

    @Override
    public void remove(Long msNo) {

    }
}
