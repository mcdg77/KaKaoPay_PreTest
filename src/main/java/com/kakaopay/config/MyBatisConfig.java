package com.kakaopay.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@MapperScan(basePackages= {"com.kakaopay.repository"}) 
public class MyBatisConfig {

	@Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setName("jpubtestdb")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("/schema-hsqldb.sql")	                            
                .addScript("/data-hsqldb.sql")
                .build();
    }

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
		
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setConfigLocation((new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml")));
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));		
        return sqlSessionFactoryBean.getObject();
		
	}
}
