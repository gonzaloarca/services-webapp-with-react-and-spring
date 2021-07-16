package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ProfessionalDto {
    private Double reviewAvg;
    private long reviewsQuantity;
    private long contractsCompleted;
    private URI user;

    public static ProfessionalDto fromUserAndRoles(User user,
                                                   Double reviewAvg,
                                                   long reviewsQuantity,
                                                   long contractsCompleted,
                                                   UriInfo uriInfo) {
        ProfessionalDto dto = new ProfessionalDto();
        dto.reviewAvg = reviewAvg;
        dto.reviewsQuantity = reviewsQuantity;
        dto.contractsCompleted = contractsCompleted;
        dto.user = uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(user.getId())).build();
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

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }
}
