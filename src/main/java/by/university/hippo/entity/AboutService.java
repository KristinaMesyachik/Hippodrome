package by.university.hippo.entity;

import by.university.hippo.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "about_service")
public class AboutService {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "cost")
    private double cost;

    @Column(name = "count_of_people")
    private int countOfPeople;

    @Column(name = "duration")
    private int duration;

    @Column(name = "enabled")
    private int enabled;
}
