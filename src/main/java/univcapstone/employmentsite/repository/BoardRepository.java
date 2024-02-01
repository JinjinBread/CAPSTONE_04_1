package univcapstone.employmentsite.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.dto.PostDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
public class BoardRepository implements PostJpaRepository {
    private final EntityManager em; //JPA

    public Optional<Post> getAllPost() {
        List<Post> posts = em.createQuery("select p from Post p", Post.class)
                .getResultList();

        return posts.stream().findAny();
    }

    public String save(Post post) {
        em.persist(post);
        return "s";
    }

    public void delete(Post post) {
        em.remove(post);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Post> entities) {

    }

    @Override
    public void deleteAll() {

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

    /**
     * 페이지 번호에 따라 10개씩 게시글 정보를 반환하는 함수
     * @param pageable
     * @return
     */
    @Override
    public List<Post> findByPost(Pageable pageable) {
        return null;
    }
    @Override
    public void flush() {

    }

    @Override
    public <S extends Post> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Post> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Post> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Post getOne(Long aLong) {
        return null;
    }

    @Override
    public Post getById(Long aLong) {
        return null;
    }

    @Override
    public Post getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Post> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Post> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Post> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Post> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Post, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Post> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public List<Post> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Post> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }
}
