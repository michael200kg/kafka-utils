package com.michael200kg.test.simpleproducer.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Profile({"tombstone"})
public class KafkaTombstoneProducer implements KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaTombstoneProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String key2, Integer partition) {

        //Map<String, String> headers = GenerationUtils.generateHeadersMap();
        //Map<String, Object> attributes = GenerationUtils.generateAttributesMap();


        //headers.keySet().forEach(key -> record.headers().add(key, headers.get(key).getBytes(StandardCharsets.UTF_8)));
           var record = new ProducerRecord<String, Object>(topicName, partition, "TOMBSTONE", null);
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(record);
            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(final SendResult<String, Object> message) {
                    System.out.println("Sent simple message= " + message + " with offset= " + message.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    System.out.println("unable to send message=" + throwable);
                }
            });

    }
}
