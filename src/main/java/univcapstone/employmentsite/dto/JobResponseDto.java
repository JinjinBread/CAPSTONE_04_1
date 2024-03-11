package univcapstone.employmentsite.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter@Setter
public class JobResponseDto {
    private Jobs jobs;

    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Jobs {
        private List<Job> job;

        public List<Job> getJob() {
            return job;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter@Setter
    public static class Job {
        private String url;
        private int active;
        private Object company;
        private Object position;
        @JsonProperty("expiration-date")
        private String expirationDate;
    }
}
