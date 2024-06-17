package nlu.hmuaf.android_bookapp.user.cart_user.Bean;

import nlu.hmuaf.android_bookapp.user.cart_user.Enum.EPaymentMethod;

public class Payments {
    private long paymentId;
    private EPaymentMethod paymentMethod;

    public Payments(long paymentId, EPaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
    }
    public Payments(){

    }
    public EPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
