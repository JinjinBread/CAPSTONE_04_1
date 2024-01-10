package univcapstone.employmentsite.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class UserDTO {
    private String id;
    private String pw;
    private String email;
    private String name;
    private String nickname;

}
