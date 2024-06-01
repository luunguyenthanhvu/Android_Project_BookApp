package nlu.hcmuaf.android_bookapp.enums;

public enum EBillStatus {
  CONFIRMING("Confirming"), PREPARING("Preparing"), DELIVERING("Delivering"), DELIVERED(
      "Delivered"), CANCELLED("Cancelled");
  private final String text;

  private EBillStatus(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
