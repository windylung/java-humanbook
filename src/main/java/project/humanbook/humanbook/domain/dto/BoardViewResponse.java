package project.humanbook.humanbook.domain.dto;

import java.time.LocalDateTime;

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

    public BoardViewResponse(Board board){
        this.id= board.getId();
        this.title=board.getTitle();
        this.content= board.getContent();
        this.createdAt = LocalDateTime.now();
        this.author=board.getOwner();
    }
}
