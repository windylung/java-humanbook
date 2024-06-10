package project.humanbook.humanbook;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.dto.BoardListViewResponse;
import project.humanbook.humanbook.domain.dto.BoardViewResponse;
import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.service.BoardService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<List<BoardListViewResponse>> getAllBoards() {
        List<BoardListViewResponse> boards = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        return ResponseEntity.ok(boards);
    }

    @PostMapping("/boards/write")
    public ResponseEntity<BoardViewResponse> createBoard(@RequestBody Board board, Authentication auth) {
        String owner = auth.getName();
        board.setOwner(owner);
        boardService.write(board);
        return ResponseEntity.ok(new BoardViewResponse(board));
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<BoardViewResponse> getBoardById(@PathVariable int id) {
        Board board = boardService.findById(id);
        return ResponseEntity.ok(new BoardViewResponse(board));
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable int id) {
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }
}


