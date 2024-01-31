package univcapstone.employmentsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="resume_id")
    private Long resumeId;

    @Column(name="user_id")
    private Long userId;

    private String resumeContent;
}
