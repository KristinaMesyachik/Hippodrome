package by.university.hippo.entity;

import by.university.hippo.entity.enums.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    @Column(name = "place")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "horse_has_service",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "horse_id"))
    private List<Horse> horses;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "staff_has_service",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id"))
    private List<Staff> staff;

    @Column(name = "enabled")
    private int enabled;
}
