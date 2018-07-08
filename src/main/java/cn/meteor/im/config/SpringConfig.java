package cn.meteor.im.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * @author Meteor
 */
@Configuration
@ComponentScan(basePackages = {"cn.meteor.im.service", "cn.meteor.im.common"})
@EnableTransactionManagement
@Import({PropertyConfig.class})
public class SpringConfig implements EnvironmentAware {

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;


    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.driverClassName = env.getProperty("jdbc.driver");
        this.jdbcUrl = env.getProperty("jdbc.url");
        this.jdbcUsername = env.getProperty("jdbc.username");
        this.jdbcPassword = env.getProperty("jdbc.password");
    }


    /**
     * 配置数据源
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    protected DataSource getDataSource() throws PropertyVetoException {
        /*ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        *//**
         * c3p0连接池的私有属性
         *//*
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(10);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setCheckoutTimeout(10000);
        dataSource.setAcquireRetryAttempts(4);*/
        logger.debug("开始配置数据源：\ndriver:{}\nurl:{}\nusername:{}\npassword:{}\n",
                driverClassName,
                jdbcUrl,
                jdbcUsername,
                jdbcPassword);

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxActive(30);
        return dataSource;
    }

    /**
     * 配置Session工厂Bean
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "sqlSessionFactoryBean")
    protected SqlSessionFactoryBean getSqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("cn.meteor.im.entity");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    /**
     * 配置MapperScanner和MyBatis通用Mapper
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
