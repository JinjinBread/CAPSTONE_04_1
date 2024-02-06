package univcapstone.employmentsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import univcapstone.employmentsite.domain.Post;

import java.util.List;
import java.util.Optional;


public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Override
    List<Post> findAll();

    @Override
    Optional<Post> findById(Long aLong);

    @Override
    void delete(Post entity);


}
