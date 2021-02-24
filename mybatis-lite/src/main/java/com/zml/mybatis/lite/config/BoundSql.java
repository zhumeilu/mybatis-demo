package com.zml.mybatis.lite.config;

import com.zml.mybatis.lite.util.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 14:11
 * @description:
 */
public class BoundSql {

	private String sqlText;
	private List<ParameterMapping> parameterMappings = new ArrayList<>();

	public BoundSql(String sqlText, List<ParameterMapping> parameterMappings) {
		this.sqlText = sqlText;
		this.parameterMappings = parameterMappings;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}
}
