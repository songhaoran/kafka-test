package com.song.produce.template.simple01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by Song on 2019/08/23.
 */
@Component
@Slf4j
public class KfkListener {

    @KafkaListener(id = "listener_01",topics = {"song_test"})
    public void listen() {

    }
}
