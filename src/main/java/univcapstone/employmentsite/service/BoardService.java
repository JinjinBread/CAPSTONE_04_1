package univcapstone.employmentsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.Reply;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.PostDto;
import univcapstone.employmentsite.repository.BoardRepository;
import univcapstone.employmentsite.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository,
                        ReplyRepository replyRepository) {
        this.boardRepository = boardRepository;
        this.replyRepository = replyRepository;
    }

    public List<Post> showAllPost(Pageable pageable) {
        return boardRepository.findAll(pageable).getContent();
    }

    public Post showPost(Long postId) {
        Post post = boardRepository.findByPostId(postId);
        List<Reply> replies=replyRepository.findByReply(postId);
        post.setReplies(replies);
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
        boardRepository.update(postId, postDto);
    }

    public Optional<Post> searchByTitle(String boardTitle) {
        Optional<Post> postResult = boardRepository.findByTitle(boardTitle);
        return postResult;
    }
}
