package by.university.hippo.entity.enums;

public enum Department {
    GROOM("Конюх"),//конюх
    HORSE_BREEDER("Коневод"),//коневод
    RIDING_MASTER("Инструктор для лошади"),//инструктор для лошади
    TRAINER("Инструктор для людей"),//инструктор для людей
    FARRIER("Ковель"),//коваль
    ACCOUNTANT("Бухгалтер"),//бухгалтер
    MANAGER("Менеджер"),//менеджер
    VETERINARIAN("Ветеринар");//ветеринар

    public final String value;

    Department(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
