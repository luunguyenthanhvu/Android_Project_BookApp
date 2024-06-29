
package nlu.hmuaf.android_bookapp.user.cart_user.enums;


public enum EBillStatus {
    CONFIRMING("Chờ xác nhận"), PREPARING("Đang chuẩn bị"),
    DELIVERING("Đang giao hàng"), DELIVERED(
            "Đã giao hàng"), CANCELLED("Đã hủy");
    private final String text;

    private EBillStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static EBillStatus valueOfLabels(String label) {
        for (EBillStatus e : values()) {
            if (e.text.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
