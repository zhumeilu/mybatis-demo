package com.zml.mybatis.lite.session;

import com.zml.mybatis.lite.config.Configuration;
import com.zml.mybatis.lite.config.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @author: maylor
 * @date: 2021/2/24 10:38
 * @description:
 */
public class SqlSessionFactoryBuilder {

	private Configuration configuration = new Configuration();


	/**
	 * 入口
	 * 第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration和MappedStatement中
	 * 第二：创建SqlSessionFactory的实现类DefaultSqlSession
	 * @return
	 */
	public SqlSessionFactory build(InputStream inputStream){

		//1.解析配置文件，封装Configuration
		XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder(configuration);
		xmlConfigBuilder.parseConfiguration(inputStream);
		//2.创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
		return sqlSessionFactory;
	}

}
