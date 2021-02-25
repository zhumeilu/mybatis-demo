package com.zml.mybatis.lite.session;

import com.zml.mybatis.lite.config.BoundSql;
import com.zml.mybatis.lite.config.Configuration;
import com.zml.mybatis.lite.config.MappedStatement;
import com.zml.mybatis.lite.util.GenericTokenParser;
import com.zml.mybatis.lite.util.ParameterMapping;
import com.zml.mybatis.lite.util.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 13:56
 * @description:
 */
public class SimpleExecutor implements Executor {

	private Connection connection = null;

	@Override public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
		List<E> result = new ArrayList<>();
		try {
			DataSource dataSource = configuration.getDataSource();
			//获取连接
			connection = dataSource.getConnection();
			//PreparedStatement接口
			String sql = mappedStatement.getSql();
			Class parameterType = mappedStatement.getParameterType();
			BoundSql boundSql = boundSql(sql);
			String sqlText = boundSql.getSqlText();
			System.out.println("sql:"+sqlText);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlText);
			//设置sql参数
			//判断该参数是否为简单对象
			if(param.getClass().isPrimitive()
					||param.getClass().isAssignableFrom(String.class)
					||param.getClass().isAssignableFrom(Character.class)
					||param instanceof Number
					){
				preparedStatement.setObject(1, param);
			}else{
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				for (int i = 0; i < parameterMappings.size(); i++) {
					String content = parameterMappings.get(i).getContent();
					Field declaredField = parameterType.getDeclaredField(content);
					declaredField.setAccessible(true);
					Object o = declaredField.get(param);
					preparedStatement.setObject(i + 1, o);
				}
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			Class resultType = mappedStatement.getResultType();
			// 遍历查询结果集
			while (resultSet.next()) {
				E o = (E) resultType.newInstance();
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				//封装结果
				for (int i = 0; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i + 1);
					Object columnValue = resultSet.getObject(columnName);
					try {
						PropertyDescriptor pd = new PropertyDescriptor(columnName, resultType);
						pd.getWriteMethod().invoke(o, columnValue);
					} catch (Exception e) {
						//						e.printStackTrace();
					}
				}
				result.add(o);
			}
			//关闭连接
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override public Integer executeUpdate(Configuration configuration, MappedStatement mappedStatement, Object param) {
		try {
			DataSource dataSource = configuration.getDataSource();
			//获取连接
			connection = dataSource.getConnection();
			//PreparedStatement接口
			Class parameterType = mappedStatement.getParameterType();
			String sql = mappedStatement.getSql();
			BoundSql boundSql = boundSql(sql);
			String sqlText = boundSql.getSqlText();
			System.out.println("sql:"+sqlText);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlText);

			//设置sql参数
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			for (int i = 0; i < parameterMappings.size(); i++) {
				String content = parameterMappings.get(i).getContent();
				Field declaredField = parameterType.getDeclaredField(content);
				declaredField.setAccessible(true);
				Object o = declaredField.get(param);
				preparedStatement.setObject(i + 1, o);
			}
			Integer execute = preparedStatement.executeUpdate();
			return execute;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭连接
			try {
				connection.close();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
		return 0;
	}

	private BoundSql boundSql(String sql) {
		//标记处理类：主要是配合通用标记解析器GenericTokenParser类完成对配置文件等的解析工作，其中TokenHandler主要完成处理
		ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
		//GenericTokenParser :通用的标记解析器，完成了代码片段中的占位符的解析，然后再根据给定的标记处理器(TokenHandler)来进行表达式的处理
		//三个参数：分别为openToken (开始标记)、closeToken (结束标记)、handler (标记处 理器)
		GenericTokenParser genericTokenParser = new GenericTokenParser("# {", "}", parameterMappingTokenHandler);
		String parse = genericTokenParser.parse(sql);
		List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
		BoundSql boundSql = new BoundSql(parse, parameterMappings);
		return boundSql;
	}

	@Override public void close() {
		try {
			connection.close();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
}
