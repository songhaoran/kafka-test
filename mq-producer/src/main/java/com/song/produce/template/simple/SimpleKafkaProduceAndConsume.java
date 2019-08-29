package com.song.produce.template.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Song on 2019/08/22.
 */
@Slf4j
public class SimpleKafkaProduceAndConsume {

    public static String group_id = "local_consumer_1";

    public static void main(String[] args) throws Exception{
        simpleTest();
    }


    public static void simpleTest() throws Exception{
        CountDownLatch latch = new CountDownLatch(4);
        ContainerProperties containerProps = new ContainerProperties("song_test");
        containerProps.setMessageListener(new MessageListener<Integer,String>() {
            @Override
            public void onMessage(ConsumerRecord<Integer, String> message) {
                log.info("[onMessage]msg->{}",message);
                latch.countDown();
            }
        });

        KafkaMessageListenerContainer<Integer, String> container = createMsgListenerContainer(containerProps);
        container.setBeanName("testAuto");
        container.start();

        KafkaTemplate<Integer, String> template = createTemplate();
        template.setDefaultTopic("song_test01");
        template.sendDefault(0, "song");
        template.sendDefault(2, "song");
        template.sendDefault(0, "song");
        template.sendDefault(2, "song");
        template.flush();
        latch.await(30, TimeUnit.SECONDS);

        container.stop();

    }


    private static KafkaMessageListenerContainer<Integer, String> createMsgListenerContainer(ContainerProperties containerProperties) {
        Map<String, Object> consumeProps = getConsumeProps();
        DefaultKafkaConsumerFactory<Integer, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumeProps);

        KafkaMessageListenerContainer<Integer, String> messageListenerContainer = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        return messageListenerContainer;
    }

    private static Map<String, Object> getConsumeProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    private static KafkaTemplate<Integer, String> createTemplate() {
        Map<String, Object> producerProps = getProducerProps();
        DefaultKafkaProducerFactory<Integer, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(producerFactory);
        return template;
    }

    private static Map<String, Object> getProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}
