package com.example.testurk.shopTest.config;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {
        "com.example.testurk"
})
public class AppConfig {
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setUser("root");
        ds.setPassword("0660106493");
        ds.setDatabaseName("shop_test");
        return ds;
    }
}
