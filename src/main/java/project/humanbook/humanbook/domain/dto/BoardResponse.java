package project.humanbook.humanbook.domain.dto;

import lombok.Getter;
import project.humanbook.humanbook.entity.Board;

@Getter
public class BoardResponse {
    //클래스 생성
    private final String title;
    private final String content;

    public BoardResponse(Board board){//엔티티를 인수로 받는 생성자
        this.title=board.getTitle();
        this.content=board.getContent();
    }
}

