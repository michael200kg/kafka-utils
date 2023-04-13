package com.michael200kg.test.kafka.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.transaction.KafkaTransactionManager;

/**
 * @author Mikhail_Vershkov
 */

@SpringBootApplication
public class TransactionApp /*implements ApplicationRunner*/ {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApp.class, args);
    }

}
