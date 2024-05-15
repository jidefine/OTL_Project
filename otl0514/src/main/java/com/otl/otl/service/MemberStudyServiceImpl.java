package com.otl.otl.service;

import com.otl.otl.domain.*;
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

//    private final MemberStudyRepository memberStudyRepository;
//    private final CustomConverters customConverters;
//
//    public MemberStudyServiceImpl(MemberStudyRepository memberStudyRepository, CustomConverters customConverters) {
//        this.memberStudyRepository = memberStudyRepository;
//        this.customConverters = customConverters;
//    }
//
//    // 컨버터
//    public StudyListDTO ProjectionToDTO(MemberStudyProjectionImpl projection) {
//        return customConverters.ProjectionToDTO(projection);
//    }
//    public StudyListDTO StudyToDto(Study study) {
//        return customConverters.StudyToDto(study);
//    }
//    public MemberStudy MemberStudyDTOToDomain(MemberStudyDTO memberStudyDTO, MemberStudy memberStudy) {
//        return customConverters.MemberStudyDTOToDomain(memberStudyDTO, memberStudy);
//    }
//
//
//
//    @Override
//    public List<MemberStudy> findByMemberEmail(String email) {
//        return memberStudyRepository.findByMemberEmail(email);
//    }
}
