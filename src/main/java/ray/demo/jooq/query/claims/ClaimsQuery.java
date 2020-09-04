package ray.demo.jooq.query.claims;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import ray.demo.jooq.domains.claims.Claim;
import ray.demo.jooq.domains.claims.ClaimType;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.val;
import static ray.demo.jooq.schema.claims.Tables.CLAIM;

@Component
@RequiredArgsConstructor
public class ClaimsQuery {
    private final DSLContext context;

    public BigDecimal sumByTypes(ClaimType... claimTypes) {
        Condition condition = CLAIM.TYPE.in(Stream.of(claimTypes).map(Enum::ordinal).toArray(Integer[]::new));
        return context.select(sum(CLAIM.AMOUNT)).from(CLAIM).where(condition).fetchOne(0, BigDecimal.class);
    }

    public List<Claim> findByUserId(UUID user_id) {
        Condition condition = CLAIM.USER_ID.eq(convert(user_id));
        return context
                .select()
                .from(CLAIM)
                .where(condition)
                .orderBy(CLAIM.CREATED.desc(), CLAIM.TYPE)
                .offset(val(0))
                .limit(val(100))
                .fetch()
                .into(Claim.class);
    }

    private static byte[] convert(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }
}
