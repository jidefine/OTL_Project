package com.otl.otl.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;           //댓글 고유번호

    @Column
    private String replyContent;    //댓글 내용

    @ManyToOne
    @JoinColumn(name = "bno")
    private Board board;            //게시물 고유번호


    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private Member member;          // 댓글 작성자

//    public void changeText(String text){
//        this.replyText = text;
//    }

}
