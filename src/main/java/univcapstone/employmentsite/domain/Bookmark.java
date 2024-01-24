package univcapstone.employmentsite.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter@Setter
@ToString
@NoArgsConstructor
public class Bookmark {
    @Id
    private Long bookmarkId;
    @NotEmpty
    public Long userId;
    @NotEmpty
    private Long postId;
}
