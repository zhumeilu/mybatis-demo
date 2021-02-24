package com.zml.mybatis.lite.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: maylor
 * @date: 2021/2/24 10:17
 * @description:存放数据库基本信息、Map<唯一标识，Mapper> 唯一标识：namespace + "." + id
 */
public class Configuration {

	private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
	private DataSource                  dataSource;

	public Map<String,MappedStatement> getMappedStatementMap() {
		return mappedStatementMap;
	}

	public void setMappedStatementMap(Map<String,MappedStatement> mappedStatementMap) {
		this.mappedStatementMap = mappedStatementMap;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
