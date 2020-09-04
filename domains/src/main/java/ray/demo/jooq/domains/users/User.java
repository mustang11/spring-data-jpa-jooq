package ray.demo.jooq.domains.users;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "user", schema = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid", length = 32, nullable = false)
    private UUID uuid;
    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    @Column(name = "gender", nullable = false)
    @Enumerated
    private Gender gender;
    @Column(name = "dob", nullable = false)
    private LocalDate dob;
}


