package univcapstone.employmentsite.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Bookmark;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    private final EntityManager em; //JPA

    public void save(Bookmark bookmark) {
        em.persist(bookmark);
    }

    public void delete(Bookmark bookmark){
        em.remove(bookmark);
    }

    public Bookmark findByBookmarkId(Long bookmarkId){
        return em.find(Bookmark.class,bookmarkId);
    }

}


