package univcapstone.employmentsite.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import univcapstone.employmentsite.domain.Bookmark;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.PostDto;
import univcapstone.employmentsite.service.BookmarkService;
import univcapstone.employmentsite.service.PostService;
import univcapstone.employmentsite.service.UserService;
import univcapstone.employmentsite.util.SessionConst;
import univcapstone.employmentsite.util.response.BasicResponse;
import univcapstone.employmentsite.util.response.DefaultResponse;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class PostController {

    private final PostService postService;
    private final BookmarkService bookmarkService;

    @Autowired
    public PostController(PostService postService,
                          BookmarkService bookmarkService
    ) {
        this.postService = postService;
        this.bookmarkService=bookmarkService;
    }

    /**
     * 게시글 목록(/boardlist 경로는 /boardlist/0(=메인페이지) /boardlist/1 (게시글 1페이지 목록.. 10개씩?)
     * @return
     */
    @GetMapping("/boardlist")
    public ResponseEntity<? extends BasicResponse> boardMain(
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
            @RequestParam(required = false, defaultValue = "lastest", value = "sort") String sort,
            @RequestParam(required = false, defaultValue = "all", value = "category") String category)
    {
        //게시글 메인화면 보기
        // /boardlist?size=10&page=1
        log.info("page={} , category={}",pageNo,category);
        PageRequest pageRequest = PageRequest.of(pageNo, 5);
        List<Post> posts = postService.showAllPost(pageRequest);

        log.info("전체 게시글 데이터 = {}", posts);

        DefaultResponse<List<Post>> defaultResponse = DefaultResponse.<List<Post>>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("보여줄 게시글 데이터")
                .result(posts)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @GetMapping("/boardlist/detail/{postId}")
    public ResponseEntity<? extends BasicResponse> board(
            @PathVariable(name = "postId") Long postId) {
        Post post = postService.showPost(postId);
        log.info("클릭한 게시물 정보:{}", post);

        DefaultResponse<Post> defaultResponse = DefaultResponse.<Post>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("회원가입 완료")
                .result(post)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @PostMapping("/boardlist/write")
    public ResponseEntity<? extends BasicResponse> boardWrite(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user,
            @RequestBody @Validated PostDto postDto
    ) {
        log.info("작성한 게시물 정보:{}", postDto);

        Post post = postService.uploadPost(user, postDto);

        DefaultResponse<Post> defaultResponse = DefaultResponse.<Post>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 작성 완료")
                .result(post)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

    @PatchMapping("/boardlist/edit/{postId}")
    public ResponseEntity<? extends BasicResponse> edit(
            @PathVariable Long postId,
            @RequestBody @Validated PostDto postDto
    ) {
        log.info("수정한 게시물 정보 = {}", postDto);

        postService.updatePost(postId, postDto);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 수정 완료")
                .result("")
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);

    }

    @DeleteMapping("/boardlist/delete/{postId}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("postId") Long postId) {
        //게시글 삭제
        log.info("삭제하려는 게시물 id : {}", postId);
        postService.deletePost(postId);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("게시글 삭제 완료")
                .result("")
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }


    @GetMapping("/boardlist/search/{boardTitle}")
    public ResponseEntity<? extends BasicResponse> search(@PathVariable String boardTitle) {
        //게시글 검색 (제목으로)
        Optional<Post> post = postService.searchByTitle(boardTitle);

        log.info("검색을 위해 입력한 단어={} ,찾은 결과물 {}", boardTitle, post);

        DefaultResponse<Optional<Post>> defaultResponse = DefaultResponse.<Optional<Post>>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("검색한 게시글들")
                .result(post)
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }

    @PostMapping("/boardlist/{postId}/bookmark")
    public ResponseEntity<? extends BasicResponse> addBookmark(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User user,
            @PathVariable Long postId
    ) {
        Post post=postService.findPostById(postId);
        Bookmark bookmark=new Bookmark();
        bookmark.setPost(post);
        bookmark.setUser(user);
        bookmarkService.saveBookmark(bookmark);

        log.info("북마크 하려는 게시물 정보={} , 북마크정보={}",post,bookmark);

        DefaultResponse<String> defaultResponse = DefaultResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("북마크 지정완료")
                .result("")
                .build();

        return ResponseEntity.ok()
                .body(defaultResponse);
    }
}