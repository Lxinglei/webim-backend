package cn.meteor.im.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * @author Meteor
 */
@Configuration
@ComponentScan(basePackages = {"cn.meteor.im.service"})
@EnableAsync
@PropertySource("classpath:jdbc.properties")
public class SpringConfig {

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;



    /**
     * 配置数据源
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    protected DataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        /**
         * c3p0连接池的私有属性
         */
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(10);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setCheckoutTimeout(10000);
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }

    /**
     * 配置Session工厂Bean
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "sqlSessionFactoryBean")
    protected SqlSessionFactoryBean getSqlSessionFactoryBean() throws PropertyVetoException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("cn.meteor.im.entity");
        return sqlSessionFactoryBean;
    }

    /**
     * 配置MapperScanne和MyBatis通用Mapper
     * @return
     */
    @Bean
    protected MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("cn.meteor.im.mapper");
        Properties properties = new Properties();
        properties.setProperty("ORDER", "BEFORE");
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }

    @Bean
    protected DataSourceTransactionManager getTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(getDataSource());
        return transactionManager;
    }
}
