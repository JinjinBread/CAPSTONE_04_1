package univcapstone.employmentsite.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Post;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByOrderByDateDesc(Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.category='resume' ORDER BY p.date DESC")
    Page<Post> findResumeByOrderByDateDesc(Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.category='interview' ORDER BY p.date DESC")
    Page<Post> findInterviewByOrderByDateDesc(Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.category='share' ORDER BY p.date DESC")
    Page<Post> findShareByOrderByDateDesc(Pageable pageable);
    Post findByPostId(Long postId);
    Optional<Post> findByTitle(String title);
}
