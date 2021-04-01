package ar.edu.itba.paw.models;

public class JobPackage {
    private long id;
    private long postId;
    private String title;
    private String description;
    private double price;
    private RateType rateType;
    private boolean is_active;

    public JobPackage() {
    }

    public JobPackage(long postId, String title, String description, double price, RateType rateType) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rateType = rateType;
        this.is_active = true;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public enum RateType {
        HOURLY,
        ONE_TIME,
        TBD
    }
}
