package com.cntaiping.tpi.edas.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tp.datasource")
public class DataSourceProperties {
	private String url;
	private String username;
	private String password;
	private Integer initialSize = 2;
	private Integer minIdle = 2;
	private Integer maxActive = 20;
	private Integer maxWait = 60000;
	private Integer timeBetweenEvictionRunsMillis = 60000;
	private Integer minEvictableIdleTimeMillis = 300000;
	private String validationQuery = "select 'x'";
	private Boolean testWhileIdle = true;
	private Boolean testOnBorrow = false;
	private Boolean testOnReturn = false;
	private Boolean poolPreparedStatements = true;
	private Integer maxPoolPreparedStatementPerConnectionSize = 20;
	private String filters = "stat";
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getInitialSize() {
		return initialSize;
	}
	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	public Integer getMaxActive() {
		return maxActive;
	}
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}
	public Integer getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}
	public void setTimeBetweenEvictionRunsMillis(
			Integer timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}
	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}
	public String getValidationQuery() {
		return validationQuery;
	}
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}
	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public Boolean getTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	public Boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}
	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}
	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}
	public void setMaxPoolPreparedStatementPerConnectionSize(
			Integer maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
}
