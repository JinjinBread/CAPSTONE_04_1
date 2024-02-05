package univcapstone.employmentsite.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.dto.ReplyPostDto;
import univcapstone.employmentsite.repository.ReplyRepository;
import univcapstone.employmentsite.repository.UserRepository;

@Slf4j
@Transactional
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }


//    public Reply saveReply(Long postId, ReplyPostDto replyData) {
//        Reply reply=new Reply(postId,
//                replyData.getReplyContent(),
//                replyData.getUserId(),
//                replyData.getReplyRefId());
//        replyRepository.save(reply);
//        return reply;
//    }
}
