package com.otl.otl.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int board_id;           // 게시물 고유번호

    @Column (nullable = false)
    private String board_title;       // 게시물 제목

    @Column (nullable = false)
    private String board_content;     // 게시물 내용

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Member member;          // 게시물 작성자

    @Column(name = "board_isDeleted", nullable = false, columnDefinition = "boolean default false") // 기본값 설정
    @Builder.Default
    private boolean isDeleted = false; // 삭제여부, 기본값 설정
}
