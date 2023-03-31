package com.yy.stock.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class AmazonDataSourceConfig {

    /**
     * 扫描spring.datasource.second开头的配置信息
     *
     * @return 数据源配置信息
     */
//    @Bean(name = "amazonDataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.amazon")
//    public DataSourceProperties amazonDataSourceProperties() {
//        return new DataSourceProperties();
//    }
    @Bean(name = "amazonHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.amazon")
    public HikariConfig amazonHikariConfig() {
        return new HikariConfig();
    }
    /**
     * 获取主库数据源对象
     *
     * @return 数据源对象
     */
    @Bean(name = "amazonDataSource")
    public DataSource amazonDataSource(@Qualifier("amazonHikariConfig") HikariConfig amazonHikariConfig) {
        return new HikariDataSource(amazonHikariConfig);
    }
    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为secondDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Bean(name = "amazonJdbcTemplate")
    public JdbcTemplate amazonJdbcTemplate(@Qualifier("amazonDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
