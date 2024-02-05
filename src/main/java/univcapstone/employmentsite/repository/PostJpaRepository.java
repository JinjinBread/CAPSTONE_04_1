package univcapstone.employmentsite.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import univcapstone.employmentsite.domain.Post;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

}
