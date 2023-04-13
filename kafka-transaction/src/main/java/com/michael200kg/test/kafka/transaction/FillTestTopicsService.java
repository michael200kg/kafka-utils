package com.michael200kg.test.kafka.transaction;

import static com.michael200kg.test.kafka.transaction.AppConfiguration.RECORD_COUNT;

/**
 * @author Mikhail_Vershkov
 */

public class FillTestTopicsService {

    private final AppProps props;
    private final KafkaSimpleProducer kafkaSimpleProducer;
    public FillTestTopicsService( KafkaSimpleProducer kafkaSimpleProducer,
                                  AppProps props) {
        this.props = props;
        this.kafkaSimpleProducer = kafkaSimpleProducer;
    }
    public void fill() {
        props.getConsumer().getTopics().forEach( topicName -> {
            for (int ii=0;ii<RECORD_COUNT;ii++) {
                kafkaSimpleProducer.send(topicName, "key-" + ii, "Test record N" + ii + " for topic " + topicName);
            }
        });
    }

}
