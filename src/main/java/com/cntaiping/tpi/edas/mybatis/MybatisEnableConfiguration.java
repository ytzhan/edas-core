package com.cntaiping.tpi.edas.mybatis;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

@Configuration
@ConditionalOnProperty(name = "tp.mybatis.enable", havingValue = "true")
public class MybatisEnableConfiguration {

	public MybatisEnableConfiguration() {
		System.out.println("MybatisEnableConfiguration  running start .....");
	}

	@Configuration
	@EnableConfigurationProperties(DataSourceProperties.class)
	public class DataSourceConfiguration {
		public DataSourceConfiguration() {
			System.out.println("DataSourceConfiguration  running start .....");
		}

		@Bean(initMethod = "init")
		public DataSource dataSource(DataSourceProperties prop)
				throws SQLException {
			DruidDataSource dataSource = new DruidDataSource();
			BeanUtils.copyProperties(prop, dataSource);
			return dataSource;
		}

		@Bean
		public PlatformTransactionManager platformTransactionManager(
				DataSource dataSource) {
			DataSourceTransactionManager dtm = new DataSourceTransactionManager(
					dataSource);
			return dtm;
		}
	}

	@Configuration
	@EnableConfigurationProperties(MybatisProperties.class)
	@Import(DAORegPostProcessor.class)
	public class MybatisConfiguration {
		public MybatisConfiguration() {
			System.out.println("MybatisConfiguration  running start .....");
		}

		@Bean
		public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(
				DataSource dataSource, MybatisProperties prop,
				ApplicationContext app) throws IOException {
			com.baomidou.mybatisplus.MybatisConfiguration configuration = new com.baomidou.mybatisplus.MybatisConfiguration();

			configuration.setCacheEnabled(prop.getCacheEnabled());
			configuration.setLazyLoadingEnabled(prop.getLazyLoadingEnabled());
			configuration.setMultipleResultSetsEnabled(prop
					.getMultipleResultSetsEnabled());
			configuration.setUseColumnLabel(prop.getUseColumnLabel());
			configuration.setDefaultExecutorType(ExecutorType.valueOf(prop
					.getDefaultExecutorType()));
			configuration.setDefaultStatementTimeout(prop
					.getDefaultStatementTimeout());

			MybatisSqlSessionFactoryBean msfb = new MybatisSqlSessionFactoryBean();
			msfb.setConfiguration(configuration);
			Resource[] ress=app.getResources(prop.getMapperLocations());
			msfb.setMapperLocations(ress);
			msfb.setDataSource(dataSource);
			msfb.setTypeAliasesPackage(prop.getTypeAliasesPackage());
			PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
			paginationInterceptor.setDialectType(prop.getDbType());
			msfb.setPlugins(new Interceptor[] { paginationInterceptor });
			GlobalConfiguration globalConfig = new GlobalConfiguration();
			globalConfig.setDbType(prop.getDbType());
			globalConfig.setIdType(prop.getIdType());
			globalConfig.setDbColumnUnderline(prop.getDbColumnUnderline());
			msfb.setGlobalConfig(globalConfig);
			return msfb;
		}
	}

}
