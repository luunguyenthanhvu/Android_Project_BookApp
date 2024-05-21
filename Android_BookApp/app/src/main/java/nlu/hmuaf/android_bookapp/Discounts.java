package nlu.hmuaf.android_bookapp;

import java.sql.Date;

public class Discounts {
    private int discountId;
    private double percent;
    private Date expireDate;
    private String description;
    private int condition;
    private String status;

    public Discounts(int discountId, double percent, Date expireDate, String description, int condition, String status) {
        this.discountId = discountId;
        this.percent = percent;
        this.expireDate = expireDate;
        this.description = description;
        this.condition = condition;
        this.status = status;
    }
    public Discounts(){

    }
    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
