package by.university.hippo.entity;

import by.university.hippo.entity.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "surname")
    private String lastname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "telephone")
    private String phone;

    @Column(name = "mail")
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;

    @Column(name = "enabled")
    private int enabled;

    public Staff(String firstname,
                 String lastname,
                 String middlename,
                 String phone,
                 String mail,
                 Department department,
                 int enabled) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.phone = phone;
        this.mail = mail;
        this.department = department;
        this.enabled = enabled;
    }
}
