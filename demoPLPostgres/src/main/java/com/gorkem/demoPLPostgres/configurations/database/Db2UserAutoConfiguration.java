package com.gorkem.demoPLPostgres.configurations.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "com.gorkem.demoPLPostgres.repository.Db2Repository",
        entityManagerFactoryRef = "secondUserEntityManager",
        transactionManagerRef = "secondUserTransactionManager"
)
public class Db2UserAutoConfiguration {

    public final Environment env;

    public Db2UserAutoConfiguration(Environment env) {
        this.env = env;
    }

    //Db2 config
    @Bean
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource secondUserDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondUserEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(secondUserDataSource());
        em.setPackagesToScan(new String[] {"com.gorkem.demoPLPostgres.entity"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto",
        env.getProperty("hibernate.hbm2ddl.auto"));

        properties.put("hibernate.dialect",
        env.getProperty("hibernate.dialect"));

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager secondUserTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(secondUserEntityManager().getObject());

        return transactionManager;
    }
}
