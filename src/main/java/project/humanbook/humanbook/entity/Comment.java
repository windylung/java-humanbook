package project.humanbook.humanbook.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.humanbook.humanbook.domain.Member;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member")
    private Member member; // 작성자

    /* 댓글 수정 */
    public void update(String comment) {
        this.comment = comment;
    }
}


    // CREATE TABLE `comments` (
    // `id` int NOT NULL AUTO_INCREMENT,
    // `comment` TEXT NOT NULL,
    // `created_date` datetime DEFAULT NULL,
    // `modified_date` datetime DEFAULT NULL,
    // `board` int DEFAULT NULL,
    // `member` bigint DEFAULT NULL,
    // PRIMARY KEY (`id`),
    // FOREIGN KEY (`board`) REFERENCES `board`(`board_id`),
    // FOREIGN KEY (`member`) REFERENCES `member`(`member_id`)
    // )