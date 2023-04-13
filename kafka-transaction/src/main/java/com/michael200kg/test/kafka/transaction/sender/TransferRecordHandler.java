package com.michael200kg.test.kafka.transaction.sender;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author Mikhail_Vershkov
 */

public class TransferRecordHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessagesCache.add(message);
    }
}
