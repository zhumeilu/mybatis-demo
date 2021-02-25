package com.zml.mybatis.lite.session;

import com.zml.mybatis.lite.config.Configuration;
import com.zml.mybatis.lite.config.MappedStatement;

import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 13:56
 * @description:
 */
public interface Executor {

	<E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param);

	Integer executeUpdate(Configuration configuration, MappedStatement mappedStatement, Object param);

	void close();
}
