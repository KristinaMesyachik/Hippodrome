package by.university.hippo.entity.enums;

public enum Category {
    TRAINING("Обучение"),
    LEISURE("Досуг");

    public final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
