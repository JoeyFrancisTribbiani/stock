package com.yy.stock.config;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.yy.stock.repository"},
        entityManagerFactoryRef = "stockeEntityManagerFactory",
        transactionManagerRef = "stockPlatformTransactionManager")
/*
basePackages 用来指定 dao 所在的位置。
entityManagerFactoryRef 用来指定实体类管理工厂 Bean 的名称
transactionManagerRef 用来指定事务管理器的引用名称，
默认的 Bean 名称为方法名
 */
public class StockJpaConfig {

    /**
     * 扫描spring.jpa.primary开头的配置信息
     *
     * @return jpa配置信息
     */
    @Primary
    @Bean(name = "stockJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.stock")
    public JpaProperties stockJpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "stockeEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean stockeEntityManagerFactory(@Qualifier("stockDataSource") DataSource stockDataSource, @Qualifier("stockJpaProperties") JpaProperties jpaProperties, EntityManagerFactoryBuilder builder) {
        return builder
                // 设置数据源
                .dataSource(stockDataSource)
                // 设置jpa配置
                .properties(jpaProperties.getProperties())
                // 设置实体包名
                .packages("com.yy.stock.entity")//设置实体类所在的位置
                // 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .persistenceUnit("stockPersistenceUnit").build();
    }

    /**
     * 获取实体管理对象
     *
     * @param factory 注入名为primaryEntityManagerFactory的bean
     * @return 实体管理对象
     */
    @Primary
    @Bean(name = "stockEntityManager")
    public EntityManager stockEntityManager(@Qualifier("stockeEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    /**
     * 获取主库事务管理对象
     *
     * @param factory 注入名为primaryEntityManagerFactory的bean
     * @return 事务管理对象
     */
    @Primary
    @Bean(name = "stockPlatformTransactionManager")
    public PlatformTransactionManager stockPlatformTransactionManager(@Qualifier("stockeEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}