package univcapstone.employmentsite.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Bookmark;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.User;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em; //JPA

    public void save(Post post) {
        em.persist(post);
    }

    public void delete(Post post){
        em.remove(post);
    }

    public void update(Post post){
        Post result = em.find(Post.class, post.getPostId());
        result.setTitle(post.getTitle());
        result.setContent(post.getContent());
        result.setDate(post.getDate());

    }
    public Post findByPostId(Long postId){
        return em.find(Post.class,postId);
    }
}
