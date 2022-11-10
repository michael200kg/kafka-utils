package com.michael200kg.test.simpleproducer;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mikhail_Vershkov
 */

@ConfigurationProperties(prefix = "spring.kafka.template")
public class KafkaProps {
    private String[] topics;

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }
}
