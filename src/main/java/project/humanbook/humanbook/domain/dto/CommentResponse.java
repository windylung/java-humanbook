package project.humanbook.humanbook.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.entity.Comment;

@RequiredArgsConstructor
@Getter
public class CommentResponse {
    private int id;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();
    private String nickname;
    private Long memberId;
    private int boardId;

    /* Entity -> Dto*/
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedDate();
        this.modifiedAt = comment.getModifiedDate();
        this.boardId = comment.getBoard().getId();
        this.nickname = comment.getMember().getLoginId();
        this.memberId = comment.getMember().getId();
    }
}