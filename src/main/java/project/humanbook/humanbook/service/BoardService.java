package project.humanbook.humanbook.service;

import project.humanbook.humanbook.entity.Board;
import project.humanbook.humanbook.repository.BoardRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board){
        boardRepository.save(board);
    }
    public List<Board> findAll(){//블로그 모든 글 조회
        return boardRepository.findAll();
    }
    public Board findById(int id){
        return boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found:"+id));
    }
    public void delete(int id){
        Board board=boardRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("not found: "+ id));
        authorizeBoardOwner(board);
        boardRepository.delete(board);
    }
    private static void authorizeBoardOwner(Board board) {
        String memberName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!board.getOwner().equals(memberName)){
            throw new IllegalArgumentException("not authorized");
        }
    }
}
