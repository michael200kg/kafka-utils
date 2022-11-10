package com.michael200kg.test.simpleproducer.utils;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.michael200kg.test.simpleproducer.model.generated.ProtobufRecord;

public class GenerationUtils {
    public static Map<String, Object> generateAttributesMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("datahub_attr_metadata_operation_id", UUID.randomUUID().toString());
        hashMap.put("datahub_attr_metadata_doc_id", 4321);
        hashMap.put("datahub_attr_metadata_tt", OffsetDateTime.now());
        hashMap.put("datahub_attr_metadata_bt", OffsetDateTime.now());
//        hashMap.put("_datahub.metadata.lsn", 123454);
//        Map<String, Object> exclusions = new HashMap<>();
//        exclusions.put("exclusion_id", UUID.randomUUID());
//        exclusions.put("exclusion_doc_id", 433341);
//        hashMap.put("_datahub.metadata.exclusions", exclusions);
//        Map<String, Object> exclusions2 = new HashMap<>();
//        exclusions2.put("exclusion2_id", UUID.randomUUID());
//        exclusions2.put("exclusion2_doc_id", 433452341);
//        exclusions.put("_datahub.metadata.exclusions.exclusions2", exclusions2);
        return hashMap;
    }

    public static Map<String, String> generateHeadersMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("datahub.metadata.tx-id", "123456");
        headers.put("datahub.metadata.operation_id", "123456");
        headers.put("datahub.metadata.operation_type", "create");
        headers.put("datahub.metadata.bt", OffsetDateTime.now().toString());
        return headers;
    }

    public static Map<String, ProtobufRecord> generateProtobufMap() {
        Map<String, ProtobufRecord> map = new HashMap<>();
        map.put("key-proto-" + Math.round(Math.random() * 1000), ProtobufRecord.newBuilder()
                        .setDocId(1)
                .setMetadataBt(OffsetDateTime.now().toString())
                .setMetadataTt(OffsetDateTime.now().toString())
                .setOperationId(UUID.randomUUID().toString())
                .build());
        return map;
    }


}
