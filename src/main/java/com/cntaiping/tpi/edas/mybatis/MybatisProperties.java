package com.cntaiping.tpi.edas.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tp.mybatis")
public class MybatisProperties {
	private String mapperLocations = "classpath*:mapper/*/*.xml";
	private Boolean cacheEnabled = true;
	private Boolean lazyLoadingEnabled = true;
	private Boolean multipleResultSetsEnabled = true;
	private Boolean useColumnLabel = true;
	private String defaultExecutorType = "SIMPLE";
	private Integer defaultStatementTimeout = 30000;

	private String typeAliasesPackage = "com.cntaiping.tpi.edas.*.mapper.po";

	private String dbType = "mysql";

	private Integer idType = 2;

	private Boolean dbColumnUnderline = false;

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Boolean getDbColumnUnderline() {
		return dbColumnUnderline;
	}

	public void setDbColumnUnderline(Boolean dbColumnUnderline) {
		this.dbColumnUnderline = dbColumnUnderline;
	}

	public String getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public Boolean getCacheEnabled() {
		return cacheEnabled;
	}

	public void setCacheEnabled(Boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}

	public Boolean getLazyLoadingEnabled() {
		return lazyLoadingEnabled;
	}

	public void setLazyLoadingEnabled(Boolean lazyLoadingEnabled) {
		this.lazyLoadingEnabled = lazyLoadingEnabled;
	}

	public Boolean getMultipleResultSetsEnabled() {
		return multipleResultSetsEnabled;
	}

	public void setMultipleResultSetsEnabled(Boolean multipleResultSetsEnabled) {
		this.multipleResultSetsEnabled = multipleResultSetsEnabled;
	}

	public Boolean getUseColumnLabel() {
		return useColumnLabel;
	}

	public void setUseColumnLabel(Boolean useColumnLabel) {
		this.useColumnLabel = useColumnLabel;
	}

	public String getDefaultExecutorType() {
		return defaultExecutorType;
	}

	public void setDefaultExecutorType(String defaultExecutorType) {
		this.defaultExecutorType = defaultExecutorType;
	}

	public Integer getDefaultStatementTimeout() {
		return defaultStatementTimeout;
	}

	public void setDefaultStatementTimeout(Integer defaultStatementTimeout) {
		this.defaultStatementTimeout = defaultStatementTimeout;
	}

	public String getTypeAliasesPackage() {
		return typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}
