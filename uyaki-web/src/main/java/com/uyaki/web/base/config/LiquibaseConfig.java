package com.uyaki.web.base.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * The type Liquibase config.
 *
 * @author uyaki
 */
@Configuration
public class LiquibaseConfig {
    /**
     * Liquibase spring liquibase.
     *
     * @param dataSource the data source
     * @return the spring liquibase
     */
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        // 数据源直接在application.yml中配置
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog("classpath:liquibase/sql/uyaki-web.sql");
        springLiquibase.setContexts("myself,develop");
        springLiquibase.setShouldRun(true);
        return springLiquibase;
    }
}
