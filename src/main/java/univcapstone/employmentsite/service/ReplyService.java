package univcapstone.employmentsite.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.dto.ReplyPostDto;
import univcapstone.employmentsite.repository.BoardRepository;
import univcapstone.employmentsite.repository.ReplyRepository;
import univcapstone.employmentsite.repository.UserRepository;

@Slf4j
@Transactional
@Service
public class ReplyService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyService(BoardRepository boardRepository, ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    public Reply saveReply(Long postId, ReplyPostDto replyDto) {

        Post post = boardRepository.getReferenceById(postId);

        Reply reply = Reply.builder()
                .post(post)
                .userId(replyDto.getUserId())
                .parentReplyId(replyDto.getParentReplyId())
                .replyContent(replyDto.getReplyContent())
                .build();

        replyRepository.save(reply);
        return reply;
    }
}
