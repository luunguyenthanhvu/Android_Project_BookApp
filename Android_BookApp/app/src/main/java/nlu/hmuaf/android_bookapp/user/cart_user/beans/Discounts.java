package nlu.hmuaf.android_bookapp.user.cart_user.beans;

import java.io.Serializable;
import java.sql.Date;

public class Discounts implements Serializable {
    private long discountId;
    private double percent;
    private Date expireDate;
    private String description;
    private int conditionDescription;
    private String status;

    public Discounts(long discountId, double percent, Date expireDate, String description, int condition, String status) {
        this.discountId = discountId;
        this.percent = percent;
        this.expireDate = expireDate;
        this.description = description;
        this.conditionDescription = condition;
        this.status = status;
    }
    public Discounts(){

    }
    public long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(long discountId) {
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
        return conditionDescription;
    }

    public void setCondition(int condition) {
        this.conditionDescription = condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
