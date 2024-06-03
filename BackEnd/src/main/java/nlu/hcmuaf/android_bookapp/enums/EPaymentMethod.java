package nlu.hcmuaf.android_bookapp.enums;

public enum EPaymentMethod {
  CREDIT_CART("Thẻ Tín Dụng"),
  COD("Thanh Toán Khi Nhận Hàng"),
  BANK_CART("Thẻ Ngân Hàng"),
  MOMO("MoMo");
  private final String text;

  private EPaymentMethod(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
