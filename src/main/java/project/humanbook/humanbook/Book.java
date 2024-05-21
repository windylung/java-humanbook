package project.humanbook.humanbook;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode를 자동으로 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함하는 생성자 자동 생성
public class Book {
  private Integer id;
  private String title;
  private String author;
  private String description;
  private String content;
}
