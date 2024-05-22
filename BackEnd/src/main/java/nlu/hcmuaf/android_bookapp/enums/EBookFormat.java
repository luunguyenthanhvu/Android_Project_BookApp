package nlu.hcmuaf.android_bookapp.enums;

public enum EBookFormat {
  HARDCOVER("HARDCOVER"),
  PAPERBACK("PAPERBACK"),
  BOX_SET("BOX_SET");
  private final String text;

  private EBookFormat(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
