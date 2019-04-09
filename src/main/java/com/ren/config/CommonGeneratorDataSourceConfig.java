package com.ren.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = { "com.ren.dao" }, sqlSessionTemplateRef = "commonGeneratorSessionTemplate")
public class CommonGeneratorDataSourceConfig {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Bean(name = "commonGeneratorDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.common-generator")
	public DataSource setDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "commonGeneratorTransactionManager")
	public DataSourceTransactionManager setTransactionManager(@Qualifier("commonGeneratorDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "commonGeneratorSqlSessionFactory")
	public SqlSessionFactory setSqlSessionFactory(@Qualifier("commonGeneratorDataSource") DataSource dataSource) {
		try {
			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			bean.setDataSource(dataSource);
			bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/common-generator/*.xml"));
			bean.setTypeAliasesPackage("com.ren.entity");
			bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
			return bean.getObject();
		} catch (Exception e) {
			logger.error("commonGeneratorSqlSessionFactory is error!", e);
		}
		return null;
	}

	@Bean(name = "commonGeneratorSessionTemplate")
	public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("commonGeneratorSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
