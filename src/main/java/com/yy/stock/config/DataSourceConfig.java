package com.yy.stock.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    //这里添加@Primary注解，一定不能少，否则在项目启动时会出错，@Primary 表示当某一个类存在多个实例时，优先使用哪个实例
    @Primary
    DataSource stockDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.amazon")
    DataSource amazonDataSource() {
        return DataSourceBuilder.create().build();
    }
}
