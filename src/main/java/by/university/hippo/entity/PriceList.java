package by.university.hippo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "price_list")
public class PriceList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "count_of_people")
    private int countOfPeople;

    @Column(name = "duration")
    private int duration;

    @Column(name = "enabled")
    private int enabled;

    @ManyToOne
    @JoinColumn(name = "about_service_id")
    private AboutService aboutService;
}