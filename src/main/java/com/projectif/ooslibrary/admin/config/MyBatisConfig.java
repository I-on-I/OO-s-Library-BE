package com.projectif.ooslibrary.admin.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
//@MapperScan(basePackages = "com.projectif.ooslibrary.admin.mapper")
//@MapperScan(basePackages = "com.projectif.ooslibrary.admin.mapper", sqlSessionTemplateRef = "mySqlSessionTemplate")
public class MyBatisConfig {

//    @Bean(name="mysqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("myDataSource") DataSource hikariDataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(hikariDataSource);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
//
//        return sqlSessionFactoryBean.getObject();
//    }

//    @Bean(name = "mySqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }

}
