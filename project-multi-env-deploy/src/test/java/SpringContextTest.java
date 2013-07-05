import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhangh
 * @createTime 2013-7-5 下午5:36:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
//对特定的profile进行初始化
@ActiveProfiles("development")
public class SpringContextTest {

	@Autowired
	BasicDataSource bds;

	@Test
	public void testDataSource() {
		System.out.println(bds.getDriverClassName());
		// test==>jdbc:mysql://192.168.2.204:3306/androidpn
		// dev ==>jdbc:mysql://192.168.2.204:3307/androidpn
		System.out.println(bds.getUrl());
	}

}
