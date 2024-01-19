package univcapstone.employmentsite.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Bookmark {
    @Id
    private Long bookmark_id;
    @NotEmpty
    public Long userId;
    @NotEmpty
    private Long postId;
}
