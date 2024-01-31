package univcapstone.employmentsite.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.Resume;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {
    private final EntityManager em;

    public void save(Resume resume) {
        em.persist(resume);
    }
}

