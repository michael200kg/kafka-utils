package com.michael200kg.test.kafka.transaction.sender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.Message;

/**
 * @author Mikhail_Vershkov
 */

public class MessagesCache {
    private static Map<String, Message<?>> messageCache = new ConcurrentHashMap<>();
    public static void add(Message<?> message) {
        messageCache.put(new String((byte[])message.getHeaders().get("kafka_receivedMessageKey")),message);
    }
    public static void remove(String key) {
        messageCache.remove(key);
    }
    public static Map<String, Message<?>> getMessages() {
        return messageCache;
    }
}
