package by.university.hippo.entity.enums;

public enum Gender {
    MARE("Конь"),
    STALLION("Лошадь");

    public final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
