package com.michael200kg.test.kafka.transaction;

import java.util.List;

/**
 * @author Mikhail_Vershkov
 */

public class ConsumerProps {
    private List<String> topics;
    private String groupId;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
