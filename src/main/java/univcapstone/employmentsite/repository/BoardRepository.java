package univcapstone.employmentsite.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.Reply;

import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOrderByDateDesc(Pageable pageable);

    Post findByPostId(Long postId);

    Optional<Post> findByTitle(String title);

}
