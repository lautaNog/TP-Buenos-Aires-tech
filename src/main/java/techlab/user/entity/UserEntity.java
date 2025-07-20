package techlab.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table (name = "USERS")
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int phone;

    public UserEntity(String userName, String name, String lastname, String email, String password, int phone) {
        this.userName = userName;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
