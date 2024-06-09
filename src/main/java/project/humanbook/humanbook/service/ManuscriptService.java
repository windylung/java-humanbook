package project.humanbook.humanbook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.humanbook.humanbook.entity.Manuscript;
import project.humanbook.humanbook.repository.ManuscriptRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManuscriptService {

    @Autowired
    private ManuscriptRepository manuscriptRepository;

    public Manuscript saveManuscript(Long userId, Integer step, String title, String content) {
        Manuscript manuscript = Manuscript.builder()
                .userId(userId)
                .step(step)
                .lastModified(LocalDateTime.now())
                .title(title)
                .content(content)
                .build();
        return manuscriptRepository.save(manuscript);
    }

    public List<Manuscript> getManuscriptsByUserId(Long userId) {
        return manuscriptRepository.findAllByUserId(userId);
    }

}
