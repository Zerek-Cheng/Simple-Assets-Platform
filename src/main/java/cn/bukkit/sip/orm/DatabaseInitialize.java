package cn.bukkit.sip.orm;

import cn.hutool.db.DbUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.internal.command.DbInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.util.Optional;


@Component
@Slf4j
public class DatabaseInitialize implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private ApplicationContext context;
    @Resource
    DataSource dataSource;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        FluentConfiguration fluentConfiguration = Flyway.configure();
        fluentConfiguration.dataSource(dataSource);
        fluentConfiguration.baselineOnMigrate(true);
        fluentConfiguration.cleanDisabled(true);
        fluentConfiguration.baselineVersion("0");
        fluentConfiguration.encoding(StandardCharsets.UTF_8);
        fluentConfiguration.validateOnMigrate(true);
        fluentConfiguration.outOfOrder(Optional.of(context.getEnvironment().getActiveProfiles())
                .map(profiles -> profiles.length > 0 && profiles[0].toLowerCase().startsWith("dev")).orElse(false));
        DatabaseMetaData databaseMetaData = dataSource.getConnection().getMetaData();
        log.info("flyway init with database type: {}, version: {}"
                , databaseMetaData.getDatabaseProductName(), databaseMetaData.getDatabaseProductVersion());
        fluentConfiguration.locations("classpath:db/migration/" + databaseMetaData.getDatabaseProductName().toLowerCase());
        fluentConfiguration.load().migrate();
    }
}
