package project.humanbook.humanbook.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.entity.Comment;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private int id;
    private String comment;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    private Member member;
    private Board board;

    public Comment toEntity() {//생성자를 사용해 객체 생성
        return Comment.builder()
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .member(member)
                .board(board)
                .build();
    }
}