package by.university.hippo.entity.enums;

public enum Status {
    READY("Готов к оплате"),
    IN_PROGRESS("В процессе");

    public final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
