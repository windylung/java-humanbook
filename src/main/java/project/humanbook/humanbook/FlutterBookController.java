package project.humanbook.humanbook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.asciidoctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.*;
import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.entity.Book;
import project.humanbook.humanbook.entity.Manuscript;
import project.humanbook.humanbook.service.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


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
    private final ManuscriptService manuscriptService;


    // response = http.get("http://humanbook.kr/book/list)
    @GetMapping("/api/book/list")
    public List<BookDto> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/api/book/{id}/content")
    public ResponseEntity<Resource> getBookContent(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookService.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Book book = optionalBook.get();
        System.out.println("book = " + book);
        byte[] epubContent = book.getEpubContent();
        if (epubContent == null) {
            epubContent = new byte[0]; // epubContent가 null인 경우 빈 배열로 초기화
        }

        ByteArrayResource resource = new ByteArrayResource(epubContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"book.epub\"")
                .body(resource);
    }


    // 업데이트(기존 책이 있는 경우)
    // cover Image
    // title 받아오기 @RequestBody
    // isLiked 어떻게 할지

//    @GetMapping("/api/book/{id}/file")
//    public ResponseEntity<Resource> getBookFile(@PathVariable Integer id) {
//        Optional<Book> optionalBook = bookService.findById(id);
//        if (!optionalBook.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Book book = optionalBook.get();
//        File file = new File(book.getFilePath());
//        if (!file.exists()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Resource resource = (Resource) new FileSystemResource(file);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                .body(resource);
//    }


    @PostMapping("/api/book/save")
    public void saveBooks(Authentication authentication) throws IOException {
        Long userId = memberService.findByLoginId(authentication.getName()).getId();
        List<Manuscript> manuscripts = manuscriptService.getManuscriptsByUserId(userId);
        Book book = new Book();
        book.setAuthor(authentication.getName());

        if (manuscripts.isEmpty()) {
            throw new IllegalArgumentException("No manuscripts found for the user.");
        }

        // AsciiDoc 콘텐츠 생성
        StringBuilder adocContent = new StringBuilder();
        adocContent.append("= Sample Book\n")
                .append(authentication.getName()).append("\n")
                .append(":doctype: book\n")
                .append(":toc: left\n")
                .append(":toclevels: 3\n\n"); // TOC 설정

        for (Manuscript manuscript : manuscripts) {
            adocContent.append("== ").append(manuscript.getTitle()).append("\n")
                    .append(manuscript.getContent()).append("\n\n");
        }

        // 임시 AsciiDoc 파일 생성
        File tempAdocFile = new File("temp.adoc");
        Files.write(tempAdocFile.toPath(), adocContent.toString().getBytes());

        // Asciidoctor 인스턴스 생성
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();

        // AsciiDoc 파일을 EPUB으로 변환
        asciidoctor.convertFile(tempAdocFile, Options.builder()
                .safe(SafeMode.SAFE)
                .backend("epub3")
                .toFile(new File("output.epub")) // 결과 파일을 지정
                .build());

        // EPUB 파일을 데이터베이스에 저장
        byte[] epubContent = Files.readAllBytes(Paths.get("output.epub"));
        book.setTitle("Sample Book");
        book.setEpubContent(epubContent);

        bookService.save(book);

        // 임시 파일 삭제
        tempAdocFile.delete();
        new File("output.epub").delete();
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

    // @GetMapping("/api/search")
    // public List<Book> searchBooks(@RequestParam(value = "keyword", required = false) String keyword,
    //                           @RequestParam(value = "type", required = false) String type) {

    //     List<Book> books = bookService.findAll();
    //     if (keyword != null && type != null) {
    //         if ("author".equalsIgnoreCase(type)) {
    //             books = searchService.searchBooksByAuthor(keyword);
    //         } else {
    //             books = searchService.searchBooksByTitle(keyword);
    //         }
    //     }
    //     return books;
    // }

    @GetMapping("/api/join") // /join에 접근할때 JoinRequset 객체 요청
    public JoinRequest joinRequest() {
        return new JoinRequest();
    }


    @PostMapping("/api/join") // 실제 회원가입 요청을 /api/join에 POST 요청
    public ResponseEntity<?> handleJoinRequest(@Valid @RequestBody JoinRequest joinRequest) {
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

