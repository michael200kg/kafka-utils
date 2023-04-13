package com.michael200kg.test.kafka.transaction;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.Scheduled;

import com.michael200kg.test.kafka.transaction.sender.TransferRecordHandler;
import com.michael200kg.test.kafka.transaction.sender.TransferRecordsProducer;
import com.michael200kg.test.kafka.transaction.transformers.SimpleTransformer;

/**
 * @author Mikhail_Vershkov
 */

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class TransferStreamConfig {

    @Autowired
    private AppProps props;

    @Bean
    public Consumer<Message<String>> transferer() {
        return message -> transferChannel().send(message);
    }

    @Bean
    MessageChannel transferChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow TransferInputFlow() {
        return IntegrationFlows
                .from(transferChannel())
                .transform(simpleTransformer())
                .handle(transferRecordHandler())
                .get();
    }

    @Bean
    public SimpleTransformer simpleTransformer() {
         return new SimpleTransformer();
    }

    public TransferRecordHandler transferRecordHandler() {
        return new TransferRecordHandler();
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public TransferRecordsProducer transferRecordsProducer() {
        return new TransferRecordsProducer(props);
    }

    @Bean(initMethod = "init")
    public SenderScheduler senderScheduler() {
        return new SenderScheduler(transferRecordsProducer());
    }

}
