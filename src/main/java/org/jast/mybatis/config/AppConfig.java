package org.jast.mybatis.config;

import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by zhiwen on 15-4-8.
 */
@Configuration
@ComponentScan(basePackages = {"org.jast.mybatis.*.*"})
public class AppConfig {

    @Bean
    public Log4jFilter log4jFilter(){
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.setResultSetLogEnabled(true);
        log4jFilter.setStatementExecuteQueryAfterLogEnabled(true);
        return log4jFilter;
    }

    /**
     * 配置Druid DataSource
     *
     * 通常来说，只需要修改initialSize、minIdle、maxActive。
     * 如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。分库分表较多的数据库，建议配置为false。
     * @return
     */
    @Bean(initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(Log4jFilter log4jFilter){
        DruidDataSource dataSource = new DruidDataSource();
        try {
            //基本属性url、user、password
            //dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_db");
            dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_db?useUnicode=true&characterEncoding=utf-8");
            dataSource.setUsername("root");
            dataSource.setPassword("zzwzzw");

            //配置初始大小
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(20);

            //配置获取连接等待超时的时间
            dataSource.setMaxWait(60000);

            //配置间隔多久进行一次检测，检测需要关闭的空连接 单位是毫秒
            dataSource.setTimeBetweenEvictionRunsMillis(60000);

            //配置一个连接在池中的最小生存时间，单位是毫秒
            dataSource.setMinEvictableIdleTimeMillis(300000);

            dataSource.setValidationQuery("SELECT 'X'");
            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(false);
            dataSource.setTestOnReturn(false);

            //打开PSCache，并且指定每个连接上PSCache的大小
            dataSource.setPoolPreparedStatements(true);
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

            //配置监控统计拦截的filters
            dataSource.setFilters("stat");
           // dataSource.setProxyFilters(Arrays.<Filter>asList(log4jFilter));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /**
     * Mybatis的SqlSession工厂
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory;
    }

    /**
     * 注入Mybatis的映射器
     *
     * MapperScannerConfigurer 会查找路径下的映射器并自动将他们创建成MapperFactoryBean
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("org.jast.mybatis.*.dao.**");
        return mapperScannerConfigurer;
    }

    /**
     * 开启Spring的事务处理
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
