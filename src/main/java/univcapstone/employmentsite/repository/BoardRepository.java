package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.Post;

import java.util.List;

public interface BoardRepository {
    //게시글 등록
    Post createPost(Post post);
    //게시글 삭제
    int deletePost();
    //게시글 수정
    Post updatePost();
    //제목으로 게시글 찾기
    List<Post> findByTitle();
    //본인이 쓴 글
    List<Post> findMyPost();
}
