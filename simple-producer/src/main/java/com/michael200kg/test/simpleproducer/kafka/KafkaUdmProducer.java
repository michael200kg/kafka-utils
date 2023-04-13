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

import com.michael200kg.test.simpleproducer.utils.GenerationUtils;

@Component
@Profile({"udm"})
public class KafkaUdmProducer implements KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final Map<String, String> RECORDS = Map.of(
            "consumer_group_lag_datahub.router.datafactory",
            "{\"name\":\"consumer_group_lag_datahub.router.datafactory\",\"type\":\"CONSUMER_GROUP_LAG\",\"resourceName\":\"datahub.router.datafactory\",\"config\":{\"topicNames\":[\"datafactory.datafactory-processor\"]}}",
            "topic_offset_increase_epm-hive.unified-notification",
            "{\"name\":\"topic_offset_increase_epm-hive.unified-notification\",\"type\":\"TOPIC_OFFSET_INCREASE\",\"resourceName\":\"epm-hive.unified-notification\",\"config\":{}}"
    );

    @Autowired
    public KafkaUdmProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String key2, Integer partition) {

        RECORDS.forEach( (key, value) -> {

            var record = new ProducerRecord<String, Object>(topicName, partition, key, value);
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

        });
    }
}
