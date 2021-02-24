package com.zml.mybatis.lite.session;

import com.zml.mybatis.lite.config.Configuration;

/**
 * @author: maylor
 * @date: 2021/2/24 10:38
 * @description:
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

	private Configuration configuration;

	public DefaultSqlSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override public SqlSession openSession(){
		return new DefaultSqlSession(configuration);
	}
}
