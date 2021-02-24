package com.zml.mybatis.lite.session;

/**
 * @author: maylor
 * @date: 2021/2/24 10:38
 * @description:
 */
public interface SqlSessionFactory {

	SqlSession openSession();
}
