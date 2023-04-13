package com.michael200kg.test.simpleproducer.kafka;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.michael200kg.test.simpleproducer.model.generated.ProtobufRecord;
import com.michael200kg.test.simpleproducer.utils.GenerationUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Primary
@Profile("protobuf")
public class KafkaProtobufProducer implements KafkaProducer {

    @Value("${spring.kafka.schema.default-schema-file}")
    private String schemaFileName;

    private final KafkaTemplate<String, ProtobufRecord> kafkaTemplate;

    @Autowired
    public KafkaProtobufProducer(KafkaTemplate<String, ProtobufRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String key22, Integer partition) {


        Map<String, String> headers = GenerationUtils.generateHeadersMap();
        Map<String, ProtobufRecord> map = GenerationUtils.generateProtobufMap();


        map.keySet().forEach(key -> {
            ProducerRecord<String, ProtobufRecord> record = new ProducerRecord<>(topicName, partition, key, map.get(key));
            headers.keySet().forEach(key2 -> record.headers().add(key2, headers.get(key2).getBytes(StandardCharsets.UTF_8)));
            ListenableFuture<SendResult<String, ProtobufRecord>> future = kafkaTemplate.send(record);
            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(final SendResult<String, ProtobufRecord> message) {
                    System.out.println("Sent message= " + message + " with offset= " + message.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    System.out.println("unable to send message=" + throwable);
                }
            });
        });

    }


}
