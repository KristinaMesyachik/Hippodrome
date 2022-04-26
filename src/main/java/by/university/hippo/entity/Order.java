package by.university.hippo.entity;

import by.university.hippo.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "amount")
    private double amount;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(name ="service_has_orders",
            joinColumns = @JoinColumn(name = "orders_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services;

    @Column(name = "user_id")
    private long userId;
}
