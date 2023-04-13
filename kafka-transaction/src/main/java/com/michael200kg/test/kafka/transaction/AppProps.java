package com.michael200kg.test.kafka.transaction;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Mikhail_Vershkov
 */
@ConfigurationProperties(prefix = "spring.kafka")
public class AppProps {
    private String bootstrapServers;
    private ProducerProps producer;
    private ConsumerProps consumer;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public ProducerProps getProducer() {
        return producer;
    }

    public void setProducer(ProducerProps producer) {
        this.producer = producer;
    }

    public ConsumerProps getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerProps consumer) {
        this.consumer = consumer;
    }
}
