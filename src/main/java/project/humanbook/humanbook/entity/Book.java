package project.humanbook.humanbook.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title")
  private String title;

  @Column(name = "author")
  private String author;

  @Lob
  @Column(name = "epubContent", nullable = false)
  private byte[] epubContent;

  @Column(name = "isLiked")
  private boolean isLiked;

  // Cover image 생략
}
