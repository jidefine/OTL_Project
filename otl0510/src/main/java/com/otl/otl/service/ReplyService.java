package com.otl.otl.service;

import com.otl.otl.domain.Reply;
import com.otl.otl.dto.ReplyDTO;
import org.springframework.data.domain.Page;

public interface ReplyService {

    Long register(ReplyDTO replyDTO); // 등록
    ReplyDTO readOne(Long replyNo); // 조회
    Reply modify(ReplyDTO replyDTO); // 수정
    void remove(Long replyNo); // 삭제

    Page<ReplyDTO> findReplys(int page, int size);
}
