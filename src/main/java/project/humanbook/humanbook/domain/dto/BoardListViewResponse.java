package project.humanbook.humanbook.domain.dto;

import lombok.Getter;
import project.humanbook.humanbook.entity.Board;

// 뷰에게 데이터 전달하는 객체
@Getter
public class BoardListViewResponse {
    private final Integer id;
    private final String owner;
    private final String title;
    private final String content;

    public BoardListViewResponse(Board board) {
        this.id = board.getId();
        this.owner = board.getOwner();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
