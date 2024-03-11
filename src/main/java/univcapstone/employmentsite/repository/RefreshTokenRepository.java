package univcapstone.employmentsite.repository;

import org.springframework.data.repository.CrudRepository;
import univcapstone.employmentsite.domain.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
