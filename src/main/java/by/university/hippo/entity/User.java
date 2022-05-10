package by.university.hippo.entity;

import by.university.hippo.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "balance")
    private double balance;

    @Column(name = "enabled")
    private int enabled;

    @OneToOne
    @JoinColumn(name = "info_id")
    private InfoUser infoUser;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @OneToMany(mappedBy = "userId"
            , cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(name ="price_list_has_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "price_list_id"))
    private List<PriceList> favorites;
}
