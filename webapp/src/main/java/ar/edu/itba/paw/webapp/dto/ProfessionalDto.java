package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserAuth;

import java.util.List;

public class ProfessionalDto extends UserDto {
    private Double reviewAvg;
    private long reviewsQuantity;
    private long contractsCompleted;

    public static ProfessionalDto fromUserAndRoles(User user,
                                                   List<UserAuth.Role> roles,
                                                   Double reviewAvg,
                                                   long reviewsQuantity,
                                                   long contractsCompleted) {
        ProfessionalDto dto = new ProfessionalDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setRoles(roles);
        dto.reviewAvg = reviewAvg;
        dto.reviewsQuantity = reviewsQuantity;
        dto.contractsCompleted = contractsCompleted;
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
}
