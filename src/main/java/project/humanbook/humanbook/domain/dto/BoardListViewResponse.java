package project.humanbook.humanbook.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import project.humanbook.humanbook.entity.Board;

// 뷰에게 데이터 전달하는 객체
@Getter
public class BoardListViewResponse {
    private final Integer id;
    private final String owner;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    private List<CommentResponse> comments;


    public BoardListViewResponse(Board board) {
        this.id = board.getId();
        this.owner = board.getOwner();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = LocalDateTime.now();
        this.comments = board.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());

    }
}
