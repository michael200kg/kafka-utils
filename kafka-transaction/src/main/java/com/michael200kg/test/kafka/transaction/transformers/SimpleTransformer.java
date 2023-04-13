package com.michael200kg.test.kafka.transaction.transformers;

import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

/**
 * @author Mikhail_Vershkov
 */

public class SimpleTransformer implements Transformer {
    @Override
    public Message<?> transform(Message<?> message) {
        return message;
    }
}
