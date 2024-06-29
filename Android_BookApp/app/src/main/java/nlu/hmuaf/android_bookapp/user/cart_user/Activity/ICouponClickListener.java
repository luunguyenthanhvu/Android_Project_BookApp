package nlu.hmuaf.android_bookapp.user.cart_user.activity;

public interface ICouponClickListener {
    void onCouponUsed( String detail, String expiredDate, String condition);
}
