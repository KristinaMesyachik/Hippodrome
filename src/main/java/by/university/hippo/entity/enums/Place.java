package by.university.hippo.entity.enums;

public enum Place {
    RIDING_HALL("Крытый манеж"),//крытый манеж
    PLAYPEN("Манеж"),//манеж
    OPEN_AREA("Открытое место");//открытое место

    public final String value;

    Place(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
