package com.michael200kg.test.simpleproducer.kafka;

import java.util.stream.IntStream;

public interface KafkaProducer {
    void send(String topicName, String key, Integer partition);
    default void send(String topicName, Integer count, Integer partitionCount) {
        IntStream.range(0,count).forEach(ii -> send(topicName, "key-" + ii, ii%partitionCount));
    }

}
