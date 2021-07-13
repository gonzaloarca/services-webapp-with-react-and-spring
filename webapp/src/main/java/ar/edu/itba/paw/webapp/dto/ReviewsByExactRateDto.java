package ar.edu.itba.paw.webapp.dto;

import java.util.List;

public class ReviewsByExactRateDto {
    private Integer one;
    private Integer two;
    private Integer three;
    private Integer four;
    private Integer five;

    public static ReviewsByExactRateDto fromListWithRates(List<Integer> reviewsByExactRate) {
        ReviewsByExactRateDto ans = new ReviewsByExactRateDto();
        ans.one = reviewsByExactRate.get(0);
        ans.two = reviewsByExactRate.get(1);
        ans.three = reviewsByExactRate.get(2);
        ans.four = reviewsByExactRate.get(3);
        ans.five = reviewsByExactRate.get(4);
        return ans;
    }

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getTwo() {
        return two;
    }

    public void setTwo(Integer two) {
        this.two = two;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getFour() {
        return four;
    }

    public void setFour(Integer four) {
        this.four = four;
    }

    public Integer getFive() {
        return five;
    }

    public void setFive(Integer five) {
        this.five = five;
    }
}
