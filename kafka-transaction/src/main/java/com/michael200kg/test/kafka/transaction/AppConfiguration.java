package com.michael200kg.test.kafka.transaction;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Mikhail_Vershkov
 */

//@Configuration
//@EnableConfigurationProperties(AppProps.class)
public class AppConfiguration {

    public static final int RECORD_COUNT = 10;

    @Autowired
    private AppProps props;

    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, props.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put("security.protocol","SASL_PLAINTEXT");
        config.put("sasl.mechanism" , "PLAIN" );

        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(config);
        producerFactory.setTransactionIdPrefix("test-transactional-id-");
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ObjectMapper defaultMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public KafkaSimpleProducer kafkaSimpleProducer() {
        return new KafkaSimpleProducer(kafkaTemplate());
    }

    @Bean
    public KafkaTransactionManager<String,String> kafkaTransactionManager() {
        return new KafkaTransactionManager<>(producerFactory());
    }

    @Bean
    TransferRecordsService transferRecordsService() {
        return new TransferRecordsService( props );
    }


    @Bean
    public FillTestTopicsService fillTestTopicsService() {
        return new FillTestTopicsService(kafkaSimpleProducer(), props);
    }
}
