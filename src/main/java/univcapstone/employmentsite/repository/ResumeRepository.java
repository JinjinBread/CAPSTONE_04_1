package univcapstone.employmentsite.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.descriptor.web.ContextEjb;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.Resume;
import univcapstone.employmentsite.domain.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {
    private final EntityManager em;

    public void save(Resume resume) {
        em.persist(resume);
    }

    public Resume getResumeByUserId(Long userId) {
        Resume resume = em.createQuery("select r from Resume r where r.userId=:userId",Resume.class)
                .setParameter("userId",userId)
                .getSingleResult();

        return resume;
    }

    public void updateResume(Long resumeId, Long id, String content) {
        Resume resume = em.find(Resume.class, resumeId);
        resume.setResumeContent(content);
    }
}

