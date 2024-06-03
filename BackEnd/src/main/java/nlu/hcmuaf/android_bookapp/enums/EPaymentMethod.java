package nlu.hcmuaf.android_bookapp.enums;

public enum EPaymentMethod {
  CREDIT_CART("Credit Card"), PAYPAL("PayPal"), VNPAY("VNPAY"), MOMO("MoMo");
  private final String text;

  private EPaymentMethod(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
