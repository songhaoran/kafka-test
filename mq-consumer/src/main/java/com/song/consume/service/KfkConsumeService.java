package com.song.consume.service;

import com.alibaba.fastjson.JSON;
import com.song.consume.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Created by Song on 2019/08/23.
 */
@Component
@Slf4j
public class KfkConsumeService {

    @KafkaListener(topics = {"song_test_1"}, groupId = "local_consumer_1", containerFactory = "kafkaListenerContainerFactory")
    public void listenSongTest(ConsumerRecord<Object, Object> data, Acknowledgment ack) {
        log.info("topic->{},data->{}", "song_test_1", JSON.toJSONString(data));
        Msg msg = JSON.parseObject(data.value().toString(), Msg.class);
        ack.acknowledge();
    }
}
