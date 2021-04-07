package ar.edu.itba.paw.webapp.form;

//import ar.edu.itba.paw.webapp.validation.ValidImage;
//import ar.edu.itba.paw.webapp.validation.ValidImageArray;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class JobPostForm {

    @NotNull
    @Pattern(regexp = "^\\d+$")
    private String jobType;

    @NotBlank
    private String title;

    @NotEmpty
    @Valid
    private List<PackageForm> packages;

    //TODO: Proximo sprint
//    @NotEmpty
//    @ValidImageArray
//    private MultipartFile[] servicePics;

    @NotBlank
    private String availableHours;

    @NotEmpty
    private int[] zones;

    @NotBlank
    private String professionalName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^\\+?[0-9- ]{7,50}")
    private String phone;

    //TODO: Proximo sprint
//    @NotNull
//    @ValidImage
//    private MultipartFile profilePic;


    public String getJobType() {
        return jobType;
    }

    public String getTitle() {
        return title;
    }

    public List<PackageForm> getPackages() {
        return packages;
    }

//    public MultipartFile[] getServicePics() {
//        return servicePics;
//    }

    public String getAvailableHours() {
        return availableHours;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

//    public MultipartFile getProfilePic() {
//        return profilePic;
//    }

    public int[] getZones() {
        return zones;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPackages(List<PackageForm> packages) {
        this.packages = packages;
    }

//    public void setServicePics(MultipartFile[] servicePics) {
//        this.servicePics = servicePics;
//    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public void setProfilePic(MultipartFile profilePic) {
//        this.profilePic = profilePic;
//    }

    public void setZones(int[] zones) {
        this.zones = zones;
    }
}
