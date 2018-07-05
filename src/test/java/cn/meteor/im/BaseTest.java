package cn.meteor.im;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(value = {"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {
}
