package univcapstone.employmentsite.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.dto.PostDto;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em; //JPA

    public Optional<Post> getAllPost() {
        List<Post> posts = em.createQuery("select p from Post p", Post.class)
                .getResultList();

        return posts.stream().findAny();
    }

    public void save(Post post) {
        em.persist(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public void update(PostDto postDto) {
        Post post = em.find(Post.class, postDto.getPostId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setFileName(postDto.getFileName());
    }

    public Post findByPostId(Long postId) {
        return em.find(Post.class, postId);
    }

    public Optional<Post> findByTitle(String boardTitle) {
        List<Post> post = em.createQuery("select p from Post p where p.title=:boardTitle", Post.class)
                .setParameter("boardTitle", boardTitle)
                .getResultList();

        return post.stream().findAny();
    }
}
