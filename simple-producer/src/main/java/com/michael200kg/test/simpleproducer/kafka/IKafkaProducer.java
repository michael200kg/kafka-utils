package com.michael200kg.test.simpleproducer.kafka;

public interface IKafkaProducer {
    void send(String topicName);
}
