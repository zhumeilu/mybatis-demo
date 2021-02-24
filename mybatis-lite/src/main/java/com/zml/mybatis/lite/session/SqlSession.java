package com.zml.mybatis.lite.session;

import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 10:40
 * @description:
 */
public interface SqlSession {

	<E> List<E> selectList(String statementId, Object param);

	<T> T selectOne(String statementId, Object params);

	<T> Integer insert(String statementId, Object params);

	<T> Integer update(String statementId, Object params);

	<T> Integer delete(String statementId, Object params);

	void close();

	<E> E getMapper(Class<E> clazz);
}
