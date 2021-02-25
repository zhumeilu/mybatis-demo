package com.zml.mybatis.lite.session;

import com.zml.mybatis.lite.config.Configuration;
import com.zml.mybatis.lite.config.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 10:49
 * @description:
 */
public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;
	private Executor      executor = new SimpleExecutor();

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override public <E> List<E> selectList(String statementId, Object param) {
		MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
		List<E> query = executor.query(configuration, mappedStatement, param);
		return query;
	}

	@Override public <T> T selectOne(String statementId, Object param) {
		MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
		List<T> query = executor.query(configuration, mappedStatement, param);
		return query.get(0);
	}

	@Override public <T> Integer insert(String statementId, Object param) {
		return this.update(statementId, param);
	}

	@Override public <T> Integer update(String statementId, Object param) {
		MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
		Integer integer = executor.executeUpdate(configuration, mappedStatement, param);
		return integer;
	}

	@Override public <T> Integer delete(String statementId, Object param) {
		return this.update(statementId, param);
	}

	@Override public void close() {
		executor.close();
	}

	@Override public <E> E getMapper(Class<E> clazz) {
		E o = (E) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new InvocationHandler() {

			@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				String methodName = method.getName();
				String clazzName = method.getDeclaringClass().getName();
				String statementId = clazzName + "#" + methodName;
				MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
				String sqlType = mappedStatement.getSqlType();
				Object param = null;
				Object result = null;
				if (args!=null&&args.length > 0) {
					param = args[0];
				}
				switch (sqlType) {
				case "select":
					if (method.getReturnType().isAssignableFrom(List.class)) {
						result = selectList(statementId, param);
					} else {
						result = selectOne(statementId, param);
					}
					break;
				case "insert":
					result = insert(statementId, param);
					break;
				case "update":
					result = update(statementId, param);
					break;
				case "delete":
					result = delete(statementId, param);
					break;
				}
				return result;
			}
		});
		return o;
	}
}
