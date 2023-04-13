package com.michael200kg.test.kafka.transaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


public class KafkaSimpleProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSimpleProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String key, String value) {

        //Map<String, String> headers = GenerationUtils.generateHeadersMap();
        //Map<String, Object> attributes = GenerationUtils.generateAttributesMap();


        //headers.keySet().forEach(key -> record.headers().add(key, headers.get(key).getBytes(StandardCharsets.UTF_8)));
           var record = new ProducerRecord<>(topicName, key, value);
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(final SendResult<String, String> message) {
                    System.out.println("Sent simple message= " + message + " with offset= " + message.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    System.out.println("unable to send message=" + throwable);
                }
            });

    }
}
