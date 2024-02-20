package univcapstone.employmentsite.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.PostDto;
import univcapstone.employmentsite.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> showAllPost(Pageable pageable) {
        List<Post> post=new ArrayList<>();
        Page<Post> pagePost= postRepository.findAllByOrderByDateDesc(pageable);

        if(pagePost!=null && pagePost.hasContent()){
            post=pagePost.getContent();
        }
        return post;
    }

    public Post showPost(Long postId) {
        Post post = postRepository.findByPostId(postId);
        if (post == null) {
            throw new IllegalStateException("해당하는 게시글을 찾을 수 없습니다.");
        }
        return post;
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findByPostId(postId);
        if (post == null) {
            throw new IllegalStateException("해당하는 게시글을 찾을 수 없습니다.");
        }
        postRepository.delete(post);
    }

    public Post uploadPost(User user, PostDto postData) {
        Post post = postData.toEntity(user, postData);
        postRepository.save(post);
        return post;
    }

    public void updatePost(Long postId, PostDto postDto) {
        Post findPost = postRepository.findByPostId(postId);
        findPost.setTitle(postDto.getTitle());
        findPost.setContent(postDto.getContent());
        findPost.setFileName(postDto.getFileName());
        postRepository.save(findPost);
    }

    public Optional<Post> searchByTitle(String boardTitle) {
        Optional<Post> postResult = postRepository.findByTitle(boardTitle);
        return postResult;
    }
}