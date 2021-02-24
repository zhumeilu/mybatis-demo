package com.zml.mybatis.test.mapper;

import com.zml.mybatis.test.domain.TestUser;

import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 10:10
 * @description:
 */
public interface TestUserMapper {

	TestUser selectOne(Integer id);
	List<TestUser> selectList();
}
