package univcapstone.employmentsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import univcapstone.employmentsite.domain.Post;


public interface PostJpaRepository extends JpaRepository<Post, Long> {



}
