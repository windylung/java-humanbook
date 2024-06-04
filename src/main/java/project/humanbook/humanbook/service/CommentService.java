package project.humanbook.humanbook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.AddCommentRequest;
import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.entity.Comment;
import project.humanbook.humanbook.repository.BoardRepository;
import project.humanbook.humanbook.repository.CommentRepository;
import project.humanbook.humanbook.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    //댓글 추가 메서드
    public Comment save(int id, AddCommentRequest request, String userName) {
        Member member = null;
        member = memberRepository.findByLoginId(userName);
        if (member == null) { // Optional이 값으로 채워져 있는지 확인
            System.out.println("사용자가 존재하지 않습니다: " + userName);
            return null;
        } 
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + id));

        request.setMember(member);
        request.setBoard(board);

        return commentRepository.save(request.toEntity());
    }
    //댓글을 읽어온다.
    @Transactional(readOnly = true)
    public List<Comment> findAll(int id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<Comment> comments = board.getComments();
        return comments;
    }

    //댓글 업데이트
    // @Transactional
    // public void update(Long articleId, Long id, UpdateCommentRequest dto) {
    //     Comment comment = commentRepository.findByArticleIdAndId(articleId, id).orElseThrow(() ->
    //             new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

    //     comment.update(dto.getComment());
    // }

    //댓글 삭제
    @Transactional
    public void delete(int boardId, int id) {
        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        authorizedArticleAuthor(comment);
        commentRepository.delete(comment);
    }
    //게시글을 작성한 유저인지 확인
    private static void authorizedArticleAuthor(Comment comment){
        String memberName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!comment.getMember().getLoginId().equals(memberName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}