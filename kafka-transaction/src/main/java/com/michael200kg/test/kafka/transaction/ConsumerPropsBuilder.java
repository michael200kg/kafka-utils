package com.michael200kg.test.kafka.transaction;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * @author Mikhail_Vershkov
 */

public class ConsumerPropsBuilder {

    public static Map<String, Object> build(AppProps props) {
        Map<String, Object> kafkaProps = new HashMap<>();

        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        kafkaProps.put("group.id", props.getConsumer().getGroupId());
        kafkaProps.put("max.poll.records", 100 );
        kafkaProps.put("auto.offset.reset", "earliest");
        kafkaProps.put("enable.auto.commit", false );
        kafkaProps.put("isolation.level", "read_committed");

        kafkaProps.put("security.protocol", "SASL_PLAINTEXT");
        kafkaProps.put("sasl.mechanism", "PLAIN");
        kafkaProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"anonymousUser\" password=\"admin\";");

        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return kafkaProps;

    }

}
