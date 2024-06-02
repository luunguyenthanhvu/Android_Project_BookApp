package nlu.hcmuaf.android_bookapp.enums;

public enum EPaymentMethod {
  CREDIT_CART("Credit Card"), COD("COD"), BANK_CART("Bank Card"), MOMO("MoMo");
  private final String text;

  private EPaymentMethod(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
