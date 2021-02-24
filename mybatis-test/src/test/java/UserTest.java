import com.zml.mybatis.lite.config.Resource;
import com.zml.mybatis.lite.session.SqlSession;
import com.zml.mybatis.lite.session.SqlSessionFactory;
import com.zml.mybatis.lite.session.SqlSessionFactoryBuilder;
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
	public void test2() {
		InputStream resourceAsSteam = Resource.getResourceAsSteam("/sqlMapConfig.xml");
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(resourceAsSteam);
		SqlSession sqlSession = factory.openSession();
		TestUserMapper mapper = sqlSession.getMapper(TestUserMapper.class);
		List<TestUser> testUsers1 = mapper.selectList();
		System.out.println(testUsers1);
	}
}
