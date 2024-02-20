package univcapstone.employmentsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import univcapstone.employmentsite.domain.Post;
import univcapstone.employmentsite.domain.Reply;

import java.util.List;

public class UserDto {

    @NotEmpty
    @Column(name = "login_id")
    private String loginId;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "replyId", cascade = CascadeType.REMOVE)
    private List<Reply> replies;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    @NotEmpty
    private String nickname;
}
