package cn.meteor.im;

import cn.meteor.im.config.PropertyConfig;
import cn.meteor.im.config.SpringConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PropertyConfig.class, SpringConfig.class})
public class BaseTest {
}
