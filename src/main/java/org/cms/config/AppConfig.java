/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cms.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.cms.dao.AdministratorDao;
import org.cms.dao.FajlDao;
import org.cms.dao.KategorijaDao;
import org.cms.dao.KomentarDao;
import org.cms.dao.KorisnikDao;
import org.cms.dao.VestDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"org.cms.*"})
@EnableTransactionManagement
@Import({SecurityConfig.class})
public class AppConfig {

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/cms_praksa");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        return dataSource;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(20000000);
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("org.cms");
        sessionBuilder.addProperties(getHibernateProperties());
        return sessionBuilder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.enable_lazy_load_no_trans", "true");
        return properties;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    @Autowired
    @Bean(name = "vestDao")
    public VestDao getVestDao(SessionFactory sessionFactory) {
        return new VestDao(sessionFactory);
    }

    @Autowired
    @Bean(name = "administratorDao")
    public AdministratorDao getAdministratorDao(SessionFactory sessionFactory) {
        return new AdministratorDao(sessionFactory);
    }

    @Autowired
    @Bean(name = "kategorijaDao")
    public KategorijaDao getKategorijaDao(SessionFactory sessionFactory) {
        return new KategorijaDao(sessionFactory);
    }

    @Autowired
    @Bean(name = "fajlDao")
    public FajlDao getFajlDao(SessionFactory sessionFactory) {
        return new FajlDao(sessionFactory);
    }

    @Autowired
    @Bean(name = "korisnikDao")
    public KorisnikDao getKorisnikDao(SessionFactory sessionFactory) {
        return new KorisnikDao(sessionFactory);
    }

    @Autowired
    @Bean(name = "komentarDao")
    public KomentarDao getKomentarDao(SessionFactory sessionFactory) {
        return new KomentarDao(sessionFactory);
    }

}
