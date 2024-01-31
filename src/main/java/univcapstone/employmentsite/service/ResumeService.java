package univcapstone.employmentsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public void getMyResume(){

    }
}
