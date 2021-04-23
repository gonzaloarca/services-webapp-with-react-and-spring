package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validation.ValidImageList;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class JobPostForm {

    @NotNull
    private Integer jobType;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Valid
    private PackageForm jobPackage;

    @ValidImageList
    private List<MultipartFile> servicePics;

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

    public PackageForm getJobPackage() {
        return jobPackage;
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

    public void setJobPackage(PackageForm jobPackage) {
        this.jobPackage = jobPackage;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public void setZones(int[] zones) {
        this.zones = zones;
    }

    public List<MultipartFile> getServicePics() {
        return servicePics;
    }

    public void setServicePics(List<MultipartFile> servicePics) {
        this.servicePics = servicePics;
    }
}
