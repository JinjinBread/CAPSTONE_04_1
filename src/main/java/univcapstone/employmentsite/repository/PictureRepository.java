package univcapstone.employmentsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Picture;
import univcapstone.employmentsite.domain.User;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    @Query("SELECT p FROM Picture p WHERE p.user.id=:userId")
    List<Picture> findAllByUserId(Long userId);
}
