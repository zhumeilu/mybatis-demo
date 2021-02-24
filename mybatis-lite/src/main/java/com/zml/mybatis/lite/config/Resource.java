package com.zml.mybatis.lite.config;

import java.io.InputStream;

/**
 * @author: maylor
 * @date: 2021/2/24 10:53
 * @description:
 */
public class Resource {

	public static InputStream getResourceAsSteam(String path) {
		InputStream resourceAsStream = Resource.class.getResourceAsStream(path);
		return resourceAsStream;
	}
}
