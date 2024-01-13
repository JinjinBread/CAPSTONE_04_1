package univcapstone.employmentsite.domain;

public class Bookmark {
    private Long bookmark_id;
    private Long userId;
    private Long postId;

    public Long getBookmark_id() {
        return bookmark_id;
    }

    public void setBookmark_id(Long bookmark_id) {
        this.bookmark_id = bookmark_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
