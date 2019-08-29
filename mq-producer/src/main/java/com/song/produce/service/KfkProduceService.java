package com.song.produce.service;

import com.alibaba.fastjson.JSON;
import com.song.produce.vo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * Created by Song on 2019/08/23.
 */
@Component
@Slf4j
public class KfkProduceService {
    @Resource
    private KafkaTemplate<Object,Object> template;

    String topic = "tenglong_test";

    public void send(Integer id,String value) {
        Msg msg = new Msg(id, value);
        String topic = "song_test_2";
        ListenableFuture<SendResult<Object, Object>> future = template.send(topic, JSON.toJSONString(msg));
//        future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.error("发送失败!");
//            }
//
//            @Override
//            public void onSuccess(SendResult<Object, Object> objectObjectSendResult) {
//                log.info("发送成功!");
//            }
//        });
        log.info("发送完成!topic->{},msg->{}",topic, JSON.toJSONString(msg));
    }
}
