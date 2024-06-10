package project.humanbook.humanbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.BoardListViewResponse;
import project.humanbook.humanbook.domain.dto.BoardViewResponse;
import project.humanbook.humanbook.domain.dto.CommentResponse;
import project.humanbook.humanbook.domain.dto.JoinRequest;
import project.humanbook.humanbook.domain.dto.LoginRequest;
import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.entity.Book;
import project.humanbook.humanbook.service.BoardService;
import project.humanbook.humanbook.service.BookService;
import project.humanbook.humanbook.service.CommentService;
import project.humanbook.humanbook.service.MemberService;
import project.humanbook.humanbook.service.SearchService;

import java.util.List;



@RequiredArgsConstructor
@RestController
@CrossOrigin(originPatterns = "*")
public class FlutterBookController {

    @Autowired
    private SearchService searchService;
    private final BookService bookService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final CommentService commentService;


    // response = http.get("http://humanbook.kr/book/list)
    @GetMapping("/api/book/list")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/test")
    public String getTest() {return "test";}


    @PostMapping("/api/login/member")
    public ResponseEntity<?> loginPage(@RequestBody LoginRequest request) {
        Member member = memberService.login(request);
        if (member == null) {
            return ResponseEntity.status(401).body("error: Invalid login credentials");
        } else {
            return ResponseEntity.ok(member);
        }
    }

    @GetMapping("/api/search")
    public List<Book> searchBooks(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "type", required = false) String type) {

        List<Book> books = bookService.findAll();
        if (keyword != null && type != null) {
            if ("author".equalsIgnoreCase(type)) {
                books = searchService.searchBooksByAuthor(keyword);
            } else {
                books = searchService.searchBooksByTitle(keyword);
            }
        }
        return books;
    }

    @GetMapping("/api/join") // /join에 접근할때 JoinRequset 객체 요청
    public JoinRequest joinRequest() {
        return new JoinRequest();
    }

    @PostMapping("/api/join") // 실제 회원가입 요청을 /api/join에 POST 요청
    public ResponseEntity<?> handleJoinRequest(JoinRequest joinRequest) {
        memberService.securityJoin(joinRequest);

        return ResponseEntity.ok("200");
    }


    @GetMapping("/api/board/getBoardList") // 게시판의 글 목록를 가져오는 API
    public List<BoardListViewResponse> returnBoard() {
        List<BoardListViewResponse> boards = boardService.findAll().stream()
        .map(BoardListViewResponse::new)
        .toList();

        System.out.println("boards = " + boards);
        // view 에서 사용하는 방법은 boardList.html에서 확인 할 수 있습니다.    

        return boards;
    }

    @GetMapping("/api/board/getBoard/{id}") // 게시판의 해당 id를 가진 글을 가져오는 API (Comment는 따로 가져와야함)
    public BoardViewResponse getBoard(@PathVariable int id){
        Board board = boardService.findById(id);
        // model.addAttribute("board",new BoardViewResponse(board)); // 화면에서 사용할 모델에 데이터를 저장한다.

        // List<CommentResponse> comments = commentService.findAll(id).stream()
        //         .map(CommentResponse::new).toList();
        // if (comments != null && !comments.isEmpty()) {
        //     model.addAttribute("comments", comments);
        // }

        return new BoardViewResponse(board);
    }

    @GetMapping("/api/board/getComment/{id}") // 게시판의 해당 id를 가진 글의 댓글을 가져오는 API
    public List<CommentResponse> getComment(@PathVariable int id) {
        List<CommentResponse> comments = commentService.findAll(id).stream()
                .map(CommentResponse::new).toList();
        if (comments != null && !comments.isEmpty()) {
            return comments;
        } else {
            return null; // view에서 null 확인 필수 !!
        }
    }


    @PostMapping("/api/board/write")
    public ResponseEntity<?> writeBoard(@RequestBody BoardRequest boardRequest, Authentication auth) {
        if(auth == null) { // null 이면 로그인이 안되어있는 상태
            return ResponseEntity.status(401).body("error: Invalid login credentials");
        }
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        Member member = memberService.getLoginMemberByLoginId(auth.getName());
        board.setOwner(auth.getName());
        boardService.write(board);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/api/board/{id}/del") // !! DELETE로 요청을 보내야함
    public ResponseEntity<Void> deleteBoard(@PathVariable int id){
        boardService.delete(id); // authentification은 BoardService에서 처리

        return ResponseEntity.ok()
                .build();
    }
    /* Comment관련 Controller는 API 폴더에 CommentApiController.java에 존재합니다. */
}

