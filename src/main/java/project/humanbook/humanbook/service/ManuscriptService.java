package project.humanbook.humanbook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.humanbook.humanbook.entity.Manuscript;
import project.humanbook.humanbook.repository.ManuscriptRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<Manuscript> getManuscriptByUserIdAndStep(Long userId, int step) {
        return manuscriptRepository.findByUserIdAndStep(userId, step);
    }

    public Manuscript updateManuscript(Long userId, int step, String title, String content, LocalDateTime localDateTime) {
        Optional<Manuscript> optionalManuscript = manuscriptRepository.findByUserIdAndStep(userId, step);
        if (optionalManuscript.isPresent()) {
            Manuscript manuscript = optionalManuscript.get();
            manuscript.setTitle(title);
            manuscript.setContent(content);
            manuscript.setLastModified(localDateTime);
            return manuscriptRepository.save(manuscript);
        } else {
            Manuscript manuscript = new Manuscript();
            manuscript.setUserId(userId);
            manuscript.setStep(step);
            manuscript.setTitle(title);
            manuscript.setContent(content);
            manuscript.setLastModified(localDateTime);
            return manuscriptRepository.save(manuscript);
        }
    }
}
