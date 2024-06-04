package project.humanbook.humanbook.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "owner")
    private String owner;

    private String title;

    private String content;

    @CreatedDate//엔티티가 생성될 때 생성 시간 저장
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate//엔티티가 수정될 때 수정 시간 저장
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    @Builder
    public Board(String owner, String title, String content){
        this.owner=owner;
        this.title=title;
        this.content=content;
    }
    public void update(String title,String content){
        this.title=title;
        this.content=content;
    }
}
