package com.zml.mybatis.lite.config;

/**
 * @author: maylor
 * @date: 2021/2/24 10:18
 * @description:sql语句、statement类型、输入参数java类型、输出参数java类型
 */
public class MappedStatement {

	/**
	 * statement_id
	 * namespace+#+select_id
	 */
	private String id;
	private String sql;
	private Class parameterType;
	private Class resultType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Class getParameterType() {
		return parameterType;
	}

	public void setParameterType(Class parameterType) {
		this.parameterType = parameterType;
	}

	public Class getResultType() {
		return resultType;
	}

	public void setResultType(Class resultType) {
		this.resultType = resultType;
	}
}
