package nlu.hcmuaf.android_bookapp.enums;

public enum EPaymentMethod {
  CREDIT_CART("Thẻ tín dụng"),
  COD("Thanh toán khi nhận hàng"),
  BANK_CART("Thẻ ngân hàng"),
  MOMO("MoMo");
  private final String text;

  private EPaymentMethod(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }

  public static EPaymentMethod valueOfLabel(String label) {
    for (EPaymentMethod e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
