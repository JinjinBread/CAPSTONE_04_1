package univcapstone.employmentsite.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.stereotype.Repository;
import univcapstone.employmentsite.domain.Bookmark;
import univcapstone.employmentsite.domain.User;

import java.util.List;
import java.util.Optional;

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

    public Optional<Bookmark> getMyBookmark(Long userId){
        List<Bookmark> bookmarks=em.createQuery("select b from Bookmark b where b.user.id = :userId", Bookmark.class)
                .setParameter("userId", userId)
                .getResultList();

        return bookmarks.stream().findAny();
    }

}


