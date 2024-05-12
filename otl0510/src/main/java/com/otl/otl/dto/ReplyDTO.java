package com.otl.otl.dto;

import com.otl.otl.domain.Board;
import com.otl.otl.domain.Member;

import java.time.LocalDateTime;

public class ReplyDTO {
    private Long replyNo;
    private String replyContent;
    private Board board;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
