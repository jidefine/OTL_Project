package com.otl.otl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private int board_id; //게시글 고유번호

    //@NotEmpty
    private String board_title; //게시글 제목

    //@NotEmpty
    private String member;  //작성자

    //@NotEmpty
    private String board_content; //게시물 내용

    private LocalDateTime regDate; //게시물 생성일

    private LocalDateTime modDate; //게시물 수정일

    private boolean isDeleted; //삭제 여부
}
