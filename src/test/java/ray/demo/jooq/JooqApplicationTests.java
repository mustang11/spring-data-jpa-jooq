package ray.demo.jooq;

import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ray.demo.jooq.domains.claims.ClaimType;

import javax.activation.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jooq.impl.DSL.count;
import static ray.demo.jooq.schema.claims.tables.Claim.CLAIM;

@SpringBootTest
class JooqApplicationTests {

    @Autowired private DSLContext dslContext;

    @Test
    void contextLoads() {
        assertThat(dslContext).isNotNull();
        BigDecimal amount = BigDecimal.valueOf(0.66);
        LocalDateTime created = LocalDate.of(2020, 1, 1).atStartOfDay();
        List<Integer> types = Stream
                .of(ClaimType.alpha, ClaimType.bravo, ClaimType.charlie)
                .map(Enum::ordinal)
                .collect(Collectors.toList());
        int count = dslContext
                .select(count())
                .from(CLAIM)
                .where(
                        CLAIM.AMOUNT.greaterOrEqual(amount),
                        CLAIM.CREATED.greaterOrEqual(created),
                        CLAIM.TYPE.in(types)
                )
                .fetchOne(0, int.class);
        assertThat(count).isEqualTo(0);
    }

}
