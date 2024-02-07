package univcapstone.employmentsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Resume;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.ReplyRepository;
import univcapstone.employmentsite.repository.ResumeRepository;

@Slf4j
@Transactional
@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume getMyResume(Long userId){
        return resumeRepository.getResumeByUserId(userId);
    }

    public void saveResume(User user, String content) {
        Resume resume=new Resume(user.getId(),content);
        resumeRepository.save(resume);
    }

    public void reviseResume(User user, String content) {
        Resume resume=resumeRepository.getResumeByUserId(user.getId());
        resumeRepository.updateResume(resume.getResumeId(),user.getId(),content);
    }
}
