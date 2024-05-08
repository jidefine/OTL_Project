package com.otl.otl.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long bno;           // 게시물 고유번호

    @Column (nullable = false)
    private String boardTitle;       // 게시물 제목

    @Column (nullable = false)
    private String boardContent;     // 게시물 내용

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false)
    private Member member;          // 게시물 작성자

    @Column(nullable = false, columnDefinition = "boolean default false") // 기본값 설정
    @Builder.Default
    private boolean isDeleted = false; // 삭제여부, 기본값 설정

    public Board(String boardTitle, String boardContent, Member member) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.member = member;
        this.isDeleted = false; // 기본값 설정
    }

    public void change(String boardTitle, String boardContent){
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void isDeleted(boolean isDeleted){
        this.isDeleted = isDeleted;
    }
}