import com.hw.springboot.Application;
import com.hw.springboot.DynamicDataSourceContextHolder;
import com.hw.springboot.service.Service1;
import com.hw.springboot.service.Service2;
import com.hw.springboot.service.Service3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huwei on 2017/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class AppTest {

    @Autowired
    Service1 service1;
    @Autowired
    Service2 service2;
    @Autowired
    Service3 service3;

    @Test
    public  void test(){

        service1.getUser(11);
        service2.getUser(11);
        service3.getUser(11);
//        DynamicDataSourceContextHolder.setDataSourceType("testDataSource2");
    }
}
