package com.michael200kg.test.kafka.transaction.sender;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerConfig;

import com.michael200kg.test.kafka.transaction.AppProps;

/**
 * @author Mikhail_Vershkov
 */

public class ProducerConfigBuilder {
    public static Properties build( AppProps props ) {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        //producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "transactional-producer-1");
        producerConfig.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 5000);
        //producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // enable idempotence
        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString()); // set transaction id
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"anonymousUser\" password=\"admin\";");
        producerConfig.put("sasl.mechanism", "PLAIN");
        producerConfig.put("security.protocol", "SASL_PLAINTEXT");

        return producerConfig;
    }
}
