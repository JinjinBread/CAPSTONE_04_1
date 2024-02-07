package univcapstone.employmentsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.PostDto;
import univcapstone.employmentsite.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Post> showAllPost(Pageable pageable) {
        return boardRepository.findAllByOrderByDateDesc(pageable).getContent();
    }

    public Post showPost(Long postId) {
        Post post = boardRepository.findByPostId(postId);
        if (post == null) {
            throw new IllegalStateException("해당하는 게시글을 찾을 수 없습니다.");
        }
        return post;
    }

    public void deletePost(Long postId) {
        Post post = boardRepository.findByPostId(postId);
        if (post == null) {
            throw new IllegalStateException("해당하는 게시글을 찾을 수 없습니다.");
        }
        boardRepository.delete(post);
    }

    public Post uploadPost(User user, PostDto postData) {
        Post post = postData.toEntity(user, postData);
        boardRepository.save(post);
        return post;
    }

    public void updatePost(Long postId, PostDto postDto) {
        Post findPost = boardRepository.findByPostId(postId);
        findPost.setTitle(postDto.getTitle());
        findPost.setContent(postDto.getContent());
        findPost.setFileName(postDto.getFileName());
        boardRepository.save(findPost);
    }

    public Optional<Post> searchByTitle(String boardTitle) {
        Optional<Post> postResult = boardRepository.findByTitle(boardTitle);
        return postResult;
    }
}
