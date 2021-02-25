package com.zml.mybatis.lite.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maylor
 * @date: 2021/2/24 11:37
 * @description:
 */
public class XmlConfigBuilder {

	private Configuration configuration;

	public XmlConfigBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	public void parseConfiguration(InputStream inputStream) {
		try {
			//通过dom4j读取
			Document document = new SAXReader().read(inputStream);
			Element rootElement = document.getRootElement();
			List<Element> propertyElements = rootElement.selectNodes("//property");
			Properties properties = new Properties();
			for (Element propertyElement : propertyElements) {
				String name = propertyElement.attributeValue("name");
				String value = propertyElement.attributeValue("value");
				properties.setProperty(name, value);
			}
			//数据源配置
			DruidDataSource dataSource = new DruidDataSource();
			dataSource.setUrl(properties.getProperty("jdbcUrl"));
			dataSource.setDriverClassName(properties.getProperty("driverClass")); //这个可以缺省的，会根据url自动识别
			dataSource.setUsername(properties.getProperty("user"));
			dataSource.setPassword(properties.getProperty("password"));
			//下面都是可选的配置
			dataSource.setInitialSize(10);  //初始连接数，默认0
			dataSource.setMaxActive(30);  //最大连接数，默认8
			dataSource.setMinIdle(10);  //最小闲置数
			dataSource.setMaxWait(2000);  //获取连接的最大等待时间，单位毫秒
			dataSource.setPoolPreparedStatements(true); //缓存PreparedStatement，默认false
			dataSource.setMaxOpenPreparedStatements(20); //缓存PreparedStatement的最大数量，默认-1（不缓存）。大于0时会自动开启缓存PreparedStatement，所以可以省略上一句代码
			configuration.setDataSource(dataSource);
			//解析mapper
			List<Element> mapperElements = rootElement.selectNodes("//mapper");
			Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
			for (Element propertyElement : mapperElements) {
				String name = propertyElement.attributeValue("resource");
				InputStream mapperInputStream = Resource.getResourceAsSteam(name);
				Document mapperDocument = new SAXReader().read(mapperInputStream);
				Element mapperRootElement = mapperDocument.getRootElement();
				String namespace = mapperRootElement.attributeValue("namespace");
				List<Element> selectElement = mapperRootElement.selectNodes("//select");
				for (Element element : selectElement) {
					String id = element.attributeValue("id");
					String parameterType = element.attributeValue("parameterType");
					String resultType = element.attributeValue("resultType");
					String sql = element.getTextTrim();
					MappedStatement mappedStatement = new MappedStatement();
					mappedStatement.setId(id);
					mappedStatement.setSql(sql);
					mappedStatement.setSqlType("select");
					if (StringUtils.isNotBlank(parameterType))
						mappedStatement.setParameterType(Class.forName(parameterType));
					if (StringUtils.isNotBlank(resultType))
						mappedStatement.setResultType(Class.forName(resultType));
					mappedStatementMap.put(namespace + "#" + id, mappedStatement);
				}
				List<Element> updateElement = mapperRootElement.selectNodes("//update");
				for (Element element : updateElement) {
					String id = element.attributeValue("id");
					String parameterType = element.attributeValue("parameterType");
					String resultType = element.attributeValue("resultType");
					String sql = element.getTextTrim();
					MappedStatement mappedStatement = new MappedStatement();
					mappedStatement.setId(id);
					mappedStatement.setSql(sql);
					mappedStatement.setSqlType("update");
					if (StringUtils.isNotBlank(parameterType))
						mappedStatement.setParameterType(Class.forName(parameterType));
					if (StringUtils.isNotBlank(resultType))
						mappedStatement.setResultType(Class.forName(resultType));
					mappedStatementMap.put(namespace + "#" + id, mappedStatement);
				}
				List<Element> insertElement = mapperRootElement.selectNodes("//insert");
				for (Element element : insertElement) {
					String id = element.attributeValue("id");
					String parameterType = element.attributeValue("parameterType");
					String resultType = element.attributeValue("resultType");
					String sql = element.getTextTrim();
					MappedStatement mappedStatement = new MappedStatement();
					mappedStatement.setId(id);
					mappedStatement.setSql(sql);
					mappedStatement.setSqlType("insert");
					if (StringUtils.isNotBlank(parameterType))
						mappedStatement.setParameterType(Class.forName(parameterType));
					if (StringUtils.isNotBlank(resultType))
						mappedStatement.setResultType(Class.forName(resultType));
					mappedStatementMap.put(namespace + "#" + id, mappedStatement);
				}
				List<Element> deleteElement = mapperRootElement.selectNodes("//delete");
				for (Element element : deleteElement) {
					String id = element.attributeValue("id");
					String parameterType = element.attributeValue("parameterType");
					String resultType = element.attributeValue("resultType");
					String sql = element.getTextTrim();
					MappedStatement mappedStatement = new MappedStatement();
					mappedStatement.setId(id);
					mappedStatement.setSql(sql);
					mappedStatement.setSqlType("delete");
					if (StringUtils.isNotBlank(parameterType))
						mappedStatement.setParameterType(Class.forName(parameterType));
					if (StringUtils.isNotBlank(resultType))
						mappedStatement.setResultType(Class.forName(resultType));
					mappedStatementMap.put(namespace + "#" + id, mappedStatement);
				}
			}
			configuration.setMappedStatementMap(mappedStatementMap);
			//			//获取连接
			//			Connection connection = dataSource.getConnection();
			//
			//			//Statement接口
			//			Statement statement = connection.createStatement();
			//			String sql1 = "insert into tb_student (name,age) values ('chy',20)";
			//			statement.executeUpdate(sql1);
			//
			//			//PreparedStatement接口
			//			String sql2 = "insert into tb_student (name,age) values ('chy',21)";
			//			PreparedStatement preparedStatement = connection.prepareStatement(sql2);
			//			preparedStatement.execute();
			//
			//			//关闭连接
			//			connection.close();
		} catch (DocumentException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
