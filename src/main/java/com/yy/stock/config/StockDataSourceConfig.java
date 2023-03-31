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
public class StockDataSourceConfig {
    /**
     * 扫描spring.datasource.primary开头的配置信息
     *
     * @return 数据源配置信息
     */
    @Primary
    @Bean(name = "stcokHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public HikariConfig stcokHikariConfig() {
        return new HikariConfig();
    }
    @Bean(name ="stockDataSource")
    //这里添加@Primary注解，一定不能少，否则在项目启动时会出错，@Primary 表示当某一个类存在多个实例时，优先使用哪个实例
    @Primary
    public DataSource stockDataSource(@Qualifier("stcokHikariConfig") HikariConfig stcokHikariConfig) {
        return new HikariDataSource(stcokHikariConfig);
    }


    /**
     * 该方法仅在需要使用JdbcTemplate对象时选用
     *
     * @param dataSource 注入名为primaryDataSource的bean
     * @return 数据源JdbcTemplate对象
     */
    @Primary
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("stockDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
