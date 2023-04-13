package com.michael200kg.test.kafka.counter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author Mikhail_Vershkov
 */

@Service
@EnableConfigurationProperties(ConsumerProps.class)
public class KafkaTopicDeletionRecordsCounter implements KafkaTopicRecordsCounter {

    @Autowired
    ConsumerProps properties;
    public long count() {

        try (KafkaConsumer<String, String> consumer =
                     new KafkaConsumer<>(ConsumerPropsBuilder.build(properties)) ) {

            List<TopicPartition> partitions = consumer.partitionsFor(properties.getTopic()).stream()
                    .map(p -> new TopicPartition(properties.getTopic(), p.partition()))
                    .collect(Collectors.toList());

            consumer.assign(partitions);

            consumer.seekToEnd(Collections.emptySet());

            Map<TopicPartition, Long> endPartitions = partitions.stream()
                    .collect(Collectors.toMap(Function.identity(), consumer::position));

            consumer.seekToBeginning(Collections.emptySet());

            return partitions.stream().mapToLong(p -> endPartitions.get(p) - consumer.position(p)).sum();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0L;
    }

}
