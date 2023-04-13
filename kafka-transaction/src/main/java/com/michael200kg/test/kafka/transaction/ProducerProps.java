package com.michael200kg.test.kafka.transaction;

import java.util.List;

/**
 * @author Mikhail_Vershkov
 */

public class ProducerProps {
    private List<String> topics;
    private String outTopic;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getOutTopic() {
        return outTopic;
    }

    public void setOutTopic(String outTopic) {
        this.outTopic = outTopic;
    }
}
