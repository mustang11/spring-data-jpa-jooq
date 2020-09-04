package ray.demo.jooq.query.users;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;
import ray.demo.jooq.domains.users.Gender;
import ray.demo.jooq.domains.users.User;

import java.util.List;

import static org.jooq.impl.DSL.val;
import static ray.demo.jooq.schema.users.Tables.USER;

@Component
@RequiredArgsConstructor
public class UserQuery {
    private final DSLContext context;

    public int countByGender(Gender gender) {
        Condition condition = USER.GENDER.eq(gender.ordinal());
        return context.selectCount().from(USER).where(condition).fetchOne(0, int.class);
    }

    public List<User> findByName(String pattern) {
        Condition condition = DSL.or(USER.FIRST_NAME.contains(pattern), USER.FIRST_NAME.contains(pattern));
        return context
                .select()
                .from(USER)
                .where(condition)
                .orderBy(USER.FIRST_NAME, USER.LAST_NAME)
                .offset(val(0))
                .limit(val(100))
                .fetch()
                .into(User.class);
    }
}
