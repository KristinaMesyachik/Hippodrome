package by.university.hippo.entity;

import by.university.hippo.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "horse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horse implements Serializable {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "age")
    private int age;

    @Column(name = "height")
    private double height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "rating")
    private double rating;

    public Horse(String nickname,
                 String breed,
                 Gender gender,
                 int age,
                 double height,
                 double weight,
                 double rating) {
        this.nickname = nickname;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.rating = rating;
    }
}
