package mashibing.com.redis_demo;

import mashibing.com.redis_demo.redis.TestRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RedisDemoApplication.class, args);
        //TestRedis redis = ctx.getBean(TestRedis.class);
        //redis.testRedis();
        
        //zk是有session的概念，没有连接池的概念
        
    }

}
