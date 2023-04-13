package com.michael200kg.test.kafka.counter;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * @author Mikhail_Vershkov
 */

//@Configuration
//@ConfigurationProperties(prefix = "spring.kafka.properties")
public class KafkaConsumerConfiguration {

    ConsumerProps properties;

//    public KafkaConsumerConfiguration(KafkaProperties kafkaProperties) {
//        this.kafkaProperties = kafkaProperties;
//    }
//
//    @Bean
//    ConsumerFactory<String, String> stringConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),
//                new StringDeserializer(),
//                new StringDeserializer());
//    }
//
//    @Bean
//    ConcurrentKafkaListenerContainerFactory<String,String> concurrentKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(stringConsumerFactory());
//        return factory;
//    }

}
