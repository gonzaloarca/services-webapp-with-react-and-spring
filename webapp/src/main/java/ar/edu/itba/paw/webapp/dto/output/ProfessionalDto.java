package ar.edu.itba.paw.webapp.dto.output;


public class ProfessionalDto {
    private Double reviewAvg;
    private long reviewsQuantity;
    private long contractsCompleted;
    public static ProfessionalDto fromUserAndRoles(Double reviewAvg,
                                                   long reviewsQuantity,
                                                   long contractsCompleted) {
        ProfessionalDto dto = new ProfessionalDto();
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
