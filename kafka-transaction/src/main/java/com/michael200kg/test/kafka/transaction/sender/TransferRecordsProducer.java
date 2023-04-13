package com.michael200kg.test.kafka.transaction.sender;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.messaging.Message;

import com.michael200kg.test.kafka.transaction.AppProps;

import static java.util.Objects.isNull;

/**
 * @author Mikhail_Vershkov
 */
public class TransferRecordsProducer {

    private final AppProps props;
    private KafkaProducer producer;

    public TransferRecordsProducer(AppProps props) {
        this.props = props;
    }

    public void init() {
        producer = new KafkaProducer<>(ProducerConfigBuilder.build(props));
        producer.initTransactions();
    }

    public void destroy() {
        producer.close();
    }

    public void transfer() {

        producer.beginTransaction();

        for(Message message : MessagesCache.getMessages().values()) {

            Long offset = (Long) message.getHeaders().get("kafka_offset");
            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

            TopicPartition partition = new TopicPartition((String) message.getHeaders().get("kafka_receivedTopic"), (Integer) message.getHeaders().get("kafka_receivedPartitionId"));

            offsets.put(partition, new OffsetAndMetadata(offset));

            String outTopic = props.getProducer().getOutTopic();
            ProducerRecord<String, String> outputRecord = new ProducerRecord<>(outTopic, new String((byte[]) message.getHeaders().get("kafka_receivedMessageKey"), StandardCharsets.UTF_8), (String) message.getPayload());
            producer.send(outputRecord, (metadata, exception) -> {
                if(isNull(exception)) {
                    System.out.println("metadata: " + metadata);
                } else {
                    System.out.println("Exception: " + exception);
                }
            });

            producer.sendOffsetsToTransaction(offsets, props.getConsumer().getGroupId());


        }
        producer.commitTransaction();

    }

}
