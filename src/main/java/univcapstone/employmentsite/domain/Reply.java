package univcapstone.employmentsite.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter@Setter
@ToString
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @Nullable
    private Long refId;
    @NotNull
    private String replyCotent;
    @CreatedDate
    private LocalDate date;

}
