package com.otl.otl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private int board_id; //식별

    private String board_title; //게시글 제목

    private String email;  //작성자

    private String board_content; //게시물 내용

    private LocalDateTime regDate; //게시물 생성일

    private LocalDateTime modDate; //게시물 수정일
}
