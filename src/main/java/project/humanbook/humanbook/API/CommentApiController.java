package project.humanbook.humanbook.API;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.dto.AddCommentRequest;
import project.humanbook.humanbook.entity.Comment;
import project.humanbook.humanbook.service.CommentService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentApiController {
    private final CommentService commentService;
    //댓글 생성
    @PostMapping("/board/{id}/comments") //+현재 인증 정보를 가져오는 principal객체
    public ResponseEntity<Comment> save(@PathVariable int id,@RequestBody AddCommentRequest request,Principal principal) {
        Comment savedComment=commentService.save(id,request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComment);
    }

    //댓글 읽어오기
    @GetMapping("/board/{id}/comments")
    public List<Comment> read(@PathVariable int id) {
        return commentService.findAll(id);
    }

    //댓글 업데이트
    // @PutMapping({"/articles/{articleId}/comments/{id}"})
    // public ResponseEntity<Long> update(@PathVariable long articleId, @PathVariable Long id, @RequestBody UpdateCommentRequest dto) {
    //     commentService.update(articleId, id, dto);
    //     return ResponseEntity.ok(id);
    // }

    //댓글 삭제
    @DeleteMapping("/board/{articleId}/comments/{id}")
    public ResponseEntity<Integer> delete(@PathVariable int articleId, @PathVariable int id) {
        commentService.delete(articleId, id);
        return ResponseEntity.ok(id);
    }
}