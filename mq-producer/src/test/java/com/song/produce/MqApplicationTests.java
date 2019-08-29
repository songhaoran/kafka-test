package com.song.produce;

import com.alibaba.fastjson.JSON;
import com.song.produce.vo.Msg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqApplicationTests {

    @Resource
    private KafkaTemplate<Object,Object> template;

    String topic = "flink_test";

    @Test
    public void contextLoads() throws Exception{
        Map<Integer, Integer> map = new HashMap<>();
        while (true) {
            Random random = new Random();
            Msg msg = new Msg(random.nextInt(10), "test");
            Integer count = map.get(msg.getId());
            if (count == null) {
                count = 0;
            }
            count += 1;
            map.put(msg.getId(), count);
            ListenableFuture<SendResult<Object, Object>> future = template.send(topic, JSON.toJSONString(msg));
            Thread.sleep(1000);

            System.out.println("***************************");
            System.out.println(JSON.toJSONString(map));
        }
    }
}
