package project.humanbook.humanbook.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.humanbook.humanbook.entity.Board;

@Getter
@NoArgsConstructor
public class BoardViewResponse {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    private List<CommentResponse> comments;

    public BoardViewResponse(Board board){
        this.id= board.getId();
        this.title=board.getTitle();
        this.content= board.getContent();
        this.createdAt = LocalDateTime.now();
        this.author=board.getOwner();
        this.comments = board.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
