package mashibing.com.redis_demo.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import mashibing.com.redis_demo.redis.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestRedis {
    
    @Autowired
    RedisTemplate redisTemplate;  //乱码
    
    //@Autowired
    //StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    @Qualifier("ooxx")
    StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    ObjectMapper objectMapper;
    
    public void testRedis() {
/*        stringRedisTemplate.opsForValue().set("hello","China");
        System.out.println(stringRedisTemplate.opsForValue().get("hello"));*/
    
/*        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
        conn.set("hello02".getBytes(),"China2".getBytes());
        System.out.println(new String(conn.get("hello02".getBytes())));*/
    
    
/*        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        hash.put("sean","name","zhangwei");
        hash.put("sean","age","12");
        System.out.println(hash.entries("sean"));*/
    
        Person p = new Person();
        p.setName("zhangsan");
        p.setAge(12);
    
        //自定义后该行可以注释掉
        //stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        
        Jackson2HashMapper jm = new Jackson2HashMapper(objectMapper, false);
        Map<String, Object> objectMap = jm.toHash(p);
    
        stringRedisTemplate.opsForHash().putAll("sean01", objectMap);
    
        Map map = stringRedisTemplate.opsForHash().entries("sean01");
    
        Person person = objectMapper.convertValue(map, Person.class);
    
        System.out.println(person.getName() + "----" + person.getAge());
        
        //stringRedisTemplate.convertAndSend("ooxx","hello");
        //stringRedisTemplate.convertAndSend("ooxx","hello2");
    
        RedisConnection cc = stringRedisTemplate.getConnectionFactory().getConnection();
        cc.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                byte[] body = message.getBody();
                System.out.println(new String(body));
            }
        }, "ooxx".getBytes());
        
        while(true){
            stringRedisTemplate.convertAndSend("ooxx","hello from self");
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
    
        }
        
    }
    
}
