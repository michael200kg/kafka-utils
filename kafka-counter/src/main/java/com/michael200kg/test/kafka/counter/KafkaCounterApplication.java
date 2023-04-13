package com.michael200kg.test.kafka.counter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Mikhail_Vershkov
 */

@SpringBootApplication
public class KafkaCounterApplication implements ApplicationRunner {

    private final KafkaTopicRecordsCounter kafkaTopicCompactionRecordsCounter;

    public KafkaCounterApplication(KafkaTopicRecordsCounter kafkaTopicCompactionRecordsCounter) {
        this.kafkaTopicCompactionRecordsCounter = kafkaTopicCompactionRecordsCounter;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaCounterApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("COUNT=" + kafkaTopicCompactionRecordsCounter.count());
    }
}
