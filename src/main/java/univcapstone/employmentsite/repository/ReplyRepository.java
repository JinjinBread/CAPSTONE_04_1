package univcapstone.employmentsite.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Reply;


@Repository
@RequiredArgsConstructor
public class ReplyRepository {
    private final EntityManager em;

    public void save(Reply reply) {
        em.persist(reply);
    }

    public Reply findByReplyId(Long replyId) {
        return em.find(Reply.class, replyId);
    }

    public void delete(Long replyId) {
        Reply reply = findByReplyId(replyId);//없으면 IllegalArgumentException 을 던짐
        em.remove(reply);
    }

    // 대댓글이 있는지 판단하는 메서드 (필요한지?)
    public boolean isExistChildReply(Long replyId) {
        // 1. 해당 게시글의 댓글들 중
        // 2. parentReplyId가 replyId인 댓글이 있으면
        // 3. 해당 댓글의 내용, 작성자(유저), 작성 시간 삭제
        return false;
    }
}
