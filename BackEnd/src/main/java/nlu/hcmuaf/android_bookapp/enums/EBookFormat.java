package nlu.hcmuaf.android_bookapp.enums;

public enum EBookFormat {
  HARDCOVER("Bìa cứng"),
  PAPERBACK("Bìa mềm"),
  BOX_SET("Hộp");
  private final String text;

  private EBookFormat(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  public static EBookFormat valueOfLabel(String label) {
    for (EBookFormat e : values()) {
      if (e.text.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
