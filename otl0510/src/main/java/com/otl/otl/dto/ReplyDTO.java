package com.otl.otl.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReplyDTO {
    private Long replyNo;
    private String replyContent;
    private Long bno;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
