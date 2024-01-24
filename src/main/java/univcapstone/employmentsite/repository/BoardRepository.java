package univcapstone.employmentsite.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Bookmark;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.User;

import java.util.List;
import java.util.Optional;

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

    public Optional<Post> findbyTitle(String boardTitle) {
        List<Post> post = em.createQuery("select p from Post p where p.title=:boardTitle",Post.class)
                .setParameter("boardTitle",boardTitle)
                .getResultList();

        return post.stream().findAny();
    }
}
