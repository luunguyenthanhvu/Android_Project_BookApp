package nlu.hcmuaf.android_bookapp.enums;

public enum ERole {
  ADMIN("ADMIN"), MANAGER("MANAGER"), USER("USER");
  private final String text;

  private ERole(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
