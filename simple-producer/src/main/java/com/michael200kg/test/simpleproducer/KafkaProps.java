package com.michael200kg.test.simpleproducer;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mikhail_Vershkov
 */

@ConfigurationProperties(prefix = "spring.kafka.template")
public class KafkaProps {
    private String[] topics;
    private Integer count;
    private Integer partitionCount;

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPartitionCount() {
        return partitionCount;
    }

    public void setPartitionCount(Integer partitionCount) {
        this.partitionCount = partitionCount;
    }
}
