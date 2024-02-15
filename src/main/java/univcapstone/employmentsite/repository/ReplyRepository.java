package univcapstone.employmentsite.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Reply;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReplyRepository {
    private final EntityManager em;

    public void save(Reply reply) {
        em.persist(reply);
    }

//    public List<Reply> findByReply(Long postId){
//        List<Reply> replies = em.createQuery("select r from Reply r where r.postId = :postId", Reply.class)
//                .setParameter("postId", postId)
//                .getResultList();
//
//        return replies.stream().collect(Collectors.toList());
//    }
}
