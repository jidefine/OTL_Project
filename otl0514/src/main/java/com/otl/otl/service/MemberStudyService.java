package com.otl.otl.service;


import com.otl.otl.domain.MemberStudy;
import com.otl.otl.dto.MemberStudyDTO;

import java.util.List;

public interface MemberStudyService {

    /*  WHERE sno = ? AND is_accpeted = 0 AND is_managed = 1 ;
       - 방장 페이지 -> 참가 대기 멤버 조회 (GET)
    */
    List<MemberStudyDTO> findWaitingParticipant(Long sno, Boolean isAccepted, Boolean isManaged);


    Long register(MemberStudyDTO memberStudyDTO);//참가신청
    MemberStudyDTO readOne(Long msNo); // 참가신청 현황(목록)
    void remove(Long msNo); // 참가신청 거절

}
