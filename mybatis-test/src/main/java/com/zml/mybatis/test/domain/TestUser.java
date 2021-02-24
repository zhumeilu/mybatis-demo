package com.zml.mybatis.test.domain;

/**
 * @author: maylor
 * @date: 2021/2/24 10:10
 * @description:
 */
public class TestUser {

	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override public String toString() {
		return "TestUser{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
