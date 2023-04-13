package com.michael200kg.test.simpleproducer.kafka;

import com.michael200kg.test.simpleproducer.utils.GenerationUtils;
import com.michael200kg.test.simpleproducer.utils.KafkaUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.AvroSchema;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
@Primary
@Profile({"avro", "km"})
public class KafkaAvroProducer implements KafkaProducer {

    @Value("${spring.kafka.schema.default-schema-file}")
    private String schemaFileName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaAvroProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String recordKey, Integer partition) {

        Map<String, String> headers = GenerationUtils.generateHeadersMap();
        Map<String, Object> attributes = GenerationUtils.generateAttributesMap();

        String stringSchema = null;
        try {
            stringSchema = KafkaUtils.getSchemaFromFile(schemaFileName);
            Schema avroSchema = new Schema.Parser().parse(stringSchema);
            List<Schema.Field> avroFields = new ArrayList<>(avroSchema.getFields());

            SchemaBuilder.FieldAssembler fieldAssembler = SchemaBuilder.builder(avroSchema.getNamespace()).record("test").fields();
            for(Schema.Field field: avroFields) {
                fieldAssembler = fieldAssembler.nullableString(field.name(),"");
            }
            for(int ii=0;ii<100;ii++) {
                fieldAssembler=fieldAssembler.nullableString("attribute_testing_for_a_long_value_"+ii,"");
            }
            Schema schema = (Schema)fieldAssembler.endRecord();

            GenericRecord genericRecord = new GenericData.Record(schema);

            attributes.keySet().forEach(key -> {
                if (attributes.get(key) instanceof OffsetDateTime) {
                    genericRecord.put(key, ((OffsetDateTime) attributes.get(key)).toInstant().getEpochSecond());
                } else {
                    genericRecord.put(key, attributes.get(key));
                }
            });

            var record = new ProducerRecord<String, Object>(topicName, partition, recordKey, genericRecord);
            headers.keySet().forEach(key -> record.headers().add(key, headers.get(key).getBytes(StandardCharsets.UTF_8)));
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(record);

            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(final SendResult<String, Object> message) {
                    System.out.println("Sent message= " + message + " with offset= " + message.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(final Throwable throwable) {
                    System.out.println("unable to send message=" + throwable);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
