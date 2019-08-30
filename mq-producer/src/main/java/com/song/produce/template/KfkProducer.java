package com.song.produce.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * Created by Song on 2019/08/21.
 */
//@Component
@Slf4j
public class KfkProducer {

    public static String SERVERS_CONFIG = "127.0.0.1:9092";

    @Bean
    public KafkaProducer getKafkaProducer(){
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS_CONFIG);//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.ACKS_CONFIG, "all");
        p.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        p.put(ProducerConfig.LINGER_MS_CONFIG, 5000);

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(p);
        return kafkaProducer;
    }
}
