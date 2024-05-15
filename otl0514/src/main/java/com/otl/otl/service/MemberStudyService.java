package com.otl.otl.service;


import com.otl.otl.dto.MemberStudyDTO;

import java.util.List;

public interface MemberStudyService {

    //List<MemberStudy> findByMemberEmail(String email);

    Long register(MemberStudyDTO memberStudyDTO);//참가신청
    MemberStudyDTO readOne(Long msNo); // 참가신청 현황(목록)
    void remove(Long msNo); // 참가신청 거절

}
