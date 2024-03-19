package univcapstone.employmentsite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @JsonIgnore
    @OneToMany(mappedBy = "postId", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "replyId", cascade = CascadeType.REMOVE)
    private List<Reply> replies;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    //@OneToMany(mappedBy = "pictureId")
    //private List<Picture> pictures;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

}
