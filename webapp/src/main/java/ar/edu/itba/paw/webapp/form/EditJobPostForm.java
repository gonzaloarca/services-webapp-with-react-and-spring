package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.ValidImageList;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class EditJobPostForm {

        @NotNull
        private Integer jobType;

        @NotBlank
        @Size(max = 100)
        private String title;

        @NotBlank
        @Size(max = 100)
        private String availableHours;

        @NotEmpty
        private int[] zones;

        public Integer getJobType() {
            return jobType;
        }

        public String getTitle() {
            return title;
        }


        public String getAvailableHours() {
            return availableHours;
        }

        public int[] getZones() {
            return zones;
        }

        public void setJobType(Integer jobType) {
            this.jobType = jobType;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public void setAvailableHours(String availableHours) {
            this.availableHours = availableHours;
        }

        public void setZones(int[] zones) {
            this.zones = zones;
        }


}
