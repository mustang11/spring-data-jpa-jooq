package ray.demo.jooq.domains.claims;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "claim", schema = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private UUID user_id;
    @Column(name = "type", nullable = false)
    @Enumerated
    private ClaimType type;
    @Column(name = "amount", precision = 2, scale = 18, nullable = false, columnDefinition = "decimal(18,2)")
    private BigDecimal amount;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;
}
