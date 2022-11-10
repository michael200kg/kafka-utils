package com.michael200kg.test.simpleproducer;

import java.util.Arrays;

import com.michael200kg.test.simpleproducer.kafka.IKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({KafkaProps.class})
public class KafkaSimpleProducerApplication implements ApplicationRunner {

    @Autowired
    KafkaProps props;

    @Autowired
    private IKafkaProducer kafkaProducer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaSimpleProducerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Arrays.asList(props.getTopics()).forEach(topic->kafkaProducer.send(topic));
    }
}
