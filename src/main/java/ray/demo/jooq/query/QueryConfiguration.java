package ray.demo.jooq.query;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.QueryPoolable;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QueryConfiguration {

    @Bean
    public static Settings settings() {
        return new Settings()
                .withDebugInfoOnStackTrace(true)
                .withExecuteLogging(true)
                .withQueryPoolable(QueryPoolable.TRUE)
                .withRenderCatalog(false);
    }

    @Bean
    public static SQLDialect sqlDialect() {
        return SQLDialect.H2;
    }

    @Bean
    public static DSLContext context(DataSource dataSource, Settings settings, SQLDialect sqlDialect) {
        return DSL.using(dataSource, sqlDialect, settings);
    }

}
