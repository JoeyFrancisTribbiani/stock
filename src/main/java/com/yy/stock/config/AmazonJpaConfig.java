package com.yy.stock.config;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.yy.stock.adaptor.amazon.repository"},
        entityManagerFactoryRef = "amazonEntityManagerFactory",
        transactionManagerRef = "amazonPlatformTransactionManager")
/*
basePackages 用来指定 dao 所在的位置。
entityManagerFactoryRef 用来指定实体类管理工厂 Bean 的名称
transactionManagerRef 用来指定事务管理器的引用名称，
默认的 Bean 名称为方法名
 */
public class AmazonJpaConfig {
    @Autowired
    @Qualifier("amazonDataSource")
    DataSource amazonDataSource;
    @Resource
    JpaProperties jpaProperties;
    @Resource
    private HibernateProperties hibernateProperties;

    @Bean
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return amazonEntityManagerFactory(builder).getObject().createEntityManager();
    }

    private Map<String, Object> getHibernateProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean amazonEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(amazonDataSource) //配置数据源
//                .properties(getHibernateProperties())
                .properties(jpaProperties.getProperties())//设置 JPA 相关配置
                .packages("com.yy.stock.adaptor.amazon.entity")//设置实体类所在的位置
                .persistenceUnit("amazonPersistenceUnit")//配置持久化单元名。若项目中只有一个 EntityManagerFactory，则 persistenceUnit 可以省略掉，若有多个，则必须明确指定持久化单元名。
                .build();
    }

    //创建一个事务管理器。JpaTransactionManager 提供对单个 EntityManagerFactory 的事务支持，专门用于解决 JPA 中的事务管理
    @Bean
    PlatformTransactionManager amazonPlatformTransactionManager(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean factory
                = amazonEntityManagerFactory(builder);
        return new JpaTransactionManager(factory.getObject());
    }

}