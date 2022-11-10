package com.michael200kg.test.simpleconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Mikhail_Vershkov
 */

@Component
public class KafkaConsumers {

    @Value("spring.kafka.consumer.topics.topic1")
    private String topic1;
    @Value("spring.kafka.consumer.topics.topic2")
    private String topic2;
    @Value("spring.kafka.consumer.topics.group1")
    private String group1;
    @Value("spring.kafka.consumer.topics.group2")
    private String group2;

    Logger logger = LoggerFactory.getLogger(KafkaConsumers.class);

    @KafkaListener(topics = "${spring.kafka.consumer.topics.topic1}",
                   groupId = "${spring.kafka.consumer.groups.group1}",
                   containerFactory = "kafkaListenerStringContainerFactory")
    public void listenGroup1(String message) {
        logger.info("Received Message from topic={} in group={}: {}", topic2, group2, message);
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topics.topic2}",
                   groupId = "${spring.kafka.consumer.groups.group2}",
                   containerFactory = "kafkaListenerStringContainerFactory")
    public void listenGroup2(String message) {
        logger.info("Received Message from topic={} in group={}: {}", topic1, group1, message);
    }

}
