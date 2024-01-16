package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.Bookmark;

import java.util.List;

public interface BookmarkRepository {
    //북마크 추가
    Bookmark addBookmark(Bookmark bookmark);
    //북마크 삭제
    int deleteBookmark();
    //자신의 북마크 보여주기
    List<Bookmark> showMyBookmark();
}
