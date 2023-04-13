package com.michael200kg.test.simpleproducer;

import java.util.Arrays;

import com.michael200kg.test.simpleproducer.kafka.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import static java.util.Objects.isNull;

@SpringBootApplication
@EnableConfigurationProperties({KafkaProps.class})
public class KafkaSimpleProducerApplication implements ApplicationRunner {

    @Autowired
    KafkaProps props;

    @Autowired
    private KafkaProducer kafkaProducer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaSimpleProducerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Arrays.asList(props.getTopics())
                .forEach(topic->kafkaProducer.send(topic, isNull(props.getCount()) ? 1 : props.getCount(), props.getPartitionCount()));
    }
}
