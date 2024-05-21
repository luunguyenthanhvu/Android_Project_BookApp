package nlu.hmuaf.android_bookapp;

public interface ICouponClickListener {
    void onCouponUsed( String detail, String expiredDate, String condition);
}
