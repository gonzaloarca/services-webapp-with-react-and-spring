package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;

import javax.ws.rs.core.UriInfo;
import java.util.List;

public class ProfessionalDto {
    private Double reviewAvg;
    private long reviewsQuantity;
    private long contractsCompleted;
    private UserDto user;
    public static ProfessionalDto fromUserAndRoles(User user,
                                                   Double reviewAvg,
                                                   long reviewsQuantity,
                                                   long contractsCompleted,
                                                   UriInfo uriInfo) {
        ProfessionalDto dto = new ProfessionalDto();
        dto.reviewAvg = reviewAvg;
        dto.reviewsQuantity = reviewsQuantity;
        dto.contractsCompleted = contractsCompleted;
        dto.user = UserDto.linkDataFromUser(user,uriInfo);
        return dto;
    }

    public Double getReviewAvg() {
        return reviewAvg;
    }

    public void setReviewAvg(Double reviewAvg) {
        this.reviewAvg = reviewAvg;
    }

    public long getReviewsQuantity() {
        return reviewsQuantity;
    }

    public void setReviewsQuantity(long reviewsQuantity) {
        this.reviewsQuantity = reviewsQuantity;
    }

    public long getContractsCompleted() {
        return contractsCompleted;
    }

    public void setContractsCompleted(long contractsCompleted) {
        this.contractsCompleted = contractsCompleted;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
