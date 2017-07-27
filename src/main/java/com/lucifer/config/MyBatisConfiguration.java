package com.lucifer.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 *
 * 获取第二个数据库的连接信息，在application.yml中配置，并指定特定的前缀
 *
 */
@Configuration
@MapperScan(basePackages={"com.h9.start.config"})
@AutoConfigureAfter({ DataBaseConfiguration.class })
@EnableTransactionManagement(proxyTargetClass=true)

public class MyBatisConfiguration implements EnvironmentAware{
    private static Log logger = LogFactory.getLog(MyBatisConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;


    @Autowired
    @Qualifier("userDataSource")
    protected DataSource userDataSource;



    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment,"mybatis.");
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(userDataSource);
            factoryBean.setTypeAliasesPackage(propertyResolver
                    .getProperty("typeAliasesPackage"));
            factoryBean
                    .setMapperLocations(new PathMatchingResourcePatternResolver()
                            .getResources(propertyResolver
                                    .getProperty("mapperLocations")));
            factoryBean
                    .setConfigLocation(new DefaultResourceLoader()
                            .getResource(propertyResolver
                                    .getProperty("configLocation")));

            SqlSessionFactory sqlSessionFactory = null;
            try {
                sqlSessionFactory = factoryBean.getObject();
            }catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            org.apache.ibatis.session.Configuration configuration = sqlSessionFactory
                    .getConfiguration();
            configuration.setMapUnderscoreToCamelCase(true);

            return sqlSessionFactory;

        } catch (Exception e) {
            logger.warn("Could not confiure mybatis session factory");
            return null;
        }
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(userDataSource);
    }


    @Bean
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(){
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate;
    }


}