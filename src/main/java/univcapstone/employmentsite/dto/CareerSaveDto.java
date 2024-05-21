package univcapstone.employmentsite.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import univcapstone.employmentsite.domain.Career;
import univcapstone.employmentsite.domain.User;

@Data
@Getter
@Setter
public class CareerSaveDto {
    private String careerName;
    private String major;
    private String startDate;
    private String endDate;
    private String careerContent;

    public Career toEntity(User user, CareerSaveDto careerSaveDto) {
        return Career.builder()
                .user(user)
                .careerName(careerSaveDto.getCareerName())
                .major(careerSaveDto.getMajor())
                .startDate(careerSaveDto.getStartDate())
                .endDate(careerSaveDto.getEndDate())
                .careerContent(careerSaveDto.getCareerContent())
                .build();
    }
}
