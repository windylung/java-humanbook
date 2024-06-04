package project.humanbook.humanbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.humanbook.humanbook.domain.dto.BoardListViewResponse;
import project.humanbook.humanbook.domain.dto.BoardViewResponse;
import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.service.BoardService;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public String returnBoard(Model model) {
        List<BoardListViewResponse> boards = boardService.findAll().stream()
        .map(BoardListViewResponse::new)
        .toList();  
        model.addAttribute("boards", boards); // 글을 model에 담음

        return "boardList";
    }
    
    @GetMapping("/board/write")
    public String writeBoard() {
        return "boardWrite";
    }

    @GetMapping("/board/{id}")
    public String getArticle(@PathVariable int id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board",new BoardViewResponse(board));//화면에서 사용할 모델에 데이터를 저장한다.
        return "board";//보여줄 화면의 템플릿 이름을 반환한다.
    }

    @DeleteMapping("/board/{id}/del")
    public ResponseEntity<Void> deleteBoard(@PathVariable int id){
        boardService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
