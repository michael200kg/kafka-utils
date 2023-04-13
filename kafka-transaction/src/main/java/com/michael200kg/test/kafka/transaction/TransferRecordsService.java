package com.michael200kg.test.kafka.transaction;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import com.michael200kg.test.kafka.transaction.sender.ProducerConfigBuilder;

import static java.util.Objects.isNull;

/**
 * @author Mikhail_Vershkov
 */

public class TransferRecordsService {

    private final AppProps props;

    public TransferRecordsService(AppProps props) {
        this.props = props;
    }

    public void transfer() {
        KafkaProducer producer = new KafkaProducer<>(ProducerConfigBuilder.build(props));
        producer.initTransactions();

        producer.beginTransaction();
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        try {
            for(String topicName : props.getConsumer().getTopics()) {


                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(ConsumerPropsBuilder.build(props));
                List<TopicPartition> partitions = consumer.partitionsFor(topicName).stream().map(p -> new TopicPartition(topicName, p.partition())).collect(Collectors.toList());
                consumer.assign(partitions);
                consumer.seekToBeginning(partitions);

                ConsumerRecords<String, String> records;
                while(true) {
                    records = consumer.poll(Duration.ofMillis(100L));
                    if(records.count() > 0) {
                        break;
                    }
                }

                consumer.endOffsets(consumer.assignment()).forEach((k,v) ->  offsets.put(k, new OffsetAndMetadata(v)) );

                records.forEach(inputRecord -> {

                    String outTopic = findOutTopic(inputRecord.topic());
                    ProducerRecord<String,String> outputRecord = new ProducerRecord<>(outTopic, inputRecord.key(), inputRecord.value());
                    Future<RecordMetadata> recordMetadataFuture = producer.send(outputRecord, (metadata, exception) -> {
                        if(isNull(exception)) {
                            System.out.println("metadata: " + metadata);
                        } else {
                            System.out.println("Exception: " + exception);
                        }
                    });
                    try {
                        recordMetadataFuture.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            producer.sendOffsetsToTransaction(offsets, props.getConsumer().getGroupId());
            producer.commitTransaction();
        } catch (Exception ex) {
            producer.abortTransaction();
        }
        finally {
            producer.close();
        }
    }

    private String findOutTopic(String inputTopic) {
        String suffix = inputTopic.substring(inputTopic.lastIndexOf("-") + 1);
        return "transaction-test.output-topic-" + suffix;
    }
}
