import com.zml.mybatis.lite.config.BoundSql;
import com.zml.mybatis.lite.config.Resource;
import com.zml.mybatis.lite.session.SqlSession;
import com.zml.mybatis.lite.session.SqlSessionFactory;
import com.zml.mybatis.lite.session.SqlSessionFactoryBuilder;
import com.zml.mybatis.lite.util.GenericTokenParser;
import com.zml.mybatis.lite.util.ParameterMapping;
import com.zml.mybatis.lite.util.ParameterMappingTokenHandler;
import com.zml.mybatis.test.domain.TestUser;
import com.zml.mybatis.test.mapper.TestUserMapper;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author: maylor
 * @date: 2021/2/24 11:00
 * @description:
 */
public class UserTest {

	@Test
	public void test() {
		InputStream resourceAsSteam = Resource.getResourceAsSteam("/sqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(resourceAsSteam);
		SqlSession sqlSession = factory.openSession();
		List<TestUser> testUsers = sqlSession.selectList("com.zml.mybatis.test.mapper.TestUserMapper#selectList", null);
		System.out.println(testUsers);
	}

	@Test
	public void testSelectList() {
		InputStream resourceAsSteam = Resource.getResourceAsSteam("/sqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(resourceAsSteam);
		SqlSession sqlSession = factory.openSession();
		TestUserMapper mapper = sqlSession.getMapper(TestUserMapper.class);
		List<TestUser> testUsers1 = mapper.selectList();
		System.out.println(testUsers1);
	}
	@Test
	public void testSelectOne() {
		InputStream resourceAsSteam = Resource.getResourceAsSteam("/sqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(resourceAsSteam);
		SqlSession sqlSession = factory.openSession();
		TestUserMapper mapper = sqlSession.getMapper(TestUserMapper.class);
		TestUser testUsers1 = mapper.selectOne(1);
		System.out.println(testUsers1);
	}
	@Test
	public void testInsert() {
		InputStream resourceAsSteam = Resource.getResourceAsSteam("/sqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(resourceAsSteam);
		SqlSession sqlSession = factory.openSession();
		TestUserMapper mapper = sqlSession.getMapper(TestUserMapper.class);
		TestUser testUser = new TestUser();
		testUser.setName("test2");
		testUser.setId(14);
		Integer insert = mapper.insert(testUser);
		System.out.println(insert);
	}

	@Test
	public void testSql(){
		String sql = "select * from test_user where id = # {id}";
		//标记处理类：主要是配合通用标记解析器GenericTokenParser类完成对配置文件等的解析工作，其中TokenHandler主要完成处理
		ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
		//GenericTokenParser :通用的标记解析器，完成了代码片段中的占位符的解析，然后再根据给定的标记处理器(TokenHandler)来进行表达式的处理
		//三个参数：分别为openToken (开始标记)、closeToken (结束标记)、handler (标记处 理器)
		GenericTokenParser genericTokenParser = new GenericTokenParser("# {", "}", parameterMappingTokenHandler);
		String parse = genericTokenParser.parse(sql);
		List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
		BoundSql boundSql = new BoundSql(parse, parameterMappings);

	}

	@Test
	public void testClass(){
		Float obj = 1f;
		System.out.println(obj instanceof Number);
	}
}
