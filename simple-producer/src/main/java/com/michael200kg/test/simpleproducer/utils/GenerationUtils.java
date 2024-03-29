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
        hashMap.put("datahub_attr_metadata_doc_id", "4321");
        hashMap.put("datahub_attr_metadata_tt", OffsetDateTime.now().toString());
        hashMap.put("datahub_attr_metadata_bt", OffsetDateTime.now().toString());
        hashMap.put("datahub_attr_text", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

//        HashMap<String,String> testMap = new HashMap<>();
//        for(int ii=0;ii<100;ii++) {
//            testMap.put("field_" + randomValue(100), "value_" + randomValue(10000));
//        }
//        hashMap.put("test_map", testMap);

        hashMap.put("json_test", "{\"name\":\"Michael\",\"sure_name\":\"Vershkov\",\"parent_name\":\"Vladimirovich\"}");
        hashMap.put("json_array_test", "[{\"name\":\"Jeka\",\"sure_name\":\"Tursun\",\"parent_name\":\"Zakirovich\"}]");
        //hashMap.put("phones", "[{\"type\": {\"id\": \"1\", \"name\": \"Mobile Phone\", \"order\": null, \"active\": true}, \"value\": \"426212585\", \"_class\": \"ApplicantPhone\"}, {\"type\": {\"id\": \"2\", \"name\": \"Business Phone\", \"order\": null, \"active\": true}, \"value\": \"333095152\", \"_class\": \"ApplicantPhone\"}, {\"type\": {\"id\": \"3\", \"name\": \"Home Phone\", \"order\": null, \"active\": true}, \"value\": \"791503685\", \"_class\": \"ApplicantPhone\"}, {\"type\": {\"id\": \"4\", \"name\": \"Other Phone\", \"order\": null, \"active\": true}, \"value\": \"190809796\", \"_class\": \"ApplicantPhone\"}]");
        hashMap.put("phones", "[{\"type\": {\"id\": \"1\", \"name\": \"Mobile Phone\", \"order\": null, \"active\": true}, \"value\": \"594317048\", \"_class\": \"ApplicantPhone\"}, {\"type\": {\"id\": \"2\", \"name\": \"Business Phone\", \"order\": null, \"active\": true}, \"value\": \"571747080\", \"_class\": \"ApplicantPhone\"}, {\"type\": {\"id\": \"3\", \"name\": \"Home Phone\", \"order\": null, \"active\": true}, \"value\": \"443540247\", \"_class\": \"ApplicantPhone\"}, {\"type\": {\"id\": \"4\", \"name\": \"Other Phone\", \"order\": null, \"active\": true}, \"value\": \"351332823\", \"_class\": \"ApplicantPhone\"}]");

//        hashMap.put("_datahub.metadata.lsn", 123454);
//        Map<String, Object> exclusions = new HashMap<>();
//        exclusions.put("exclusion_id", UUID.randomUUID());
//        exclusions.put("exclusion_doc_id", 433341);
//        hashMap.put("_datahub.metadata.exclusions", exclusions);
//        Map<String, Object> exclusions2 = new HashMap<>();
//        exclusions2.put("exclusion2_id", UUID.randomUUID());
//        exclusions2.put("exclusion2_doc_id", 433452341);
//        exclusions.put("_datahub.metadata.exclusions.exclusions2", exclusions2);

        for(int ii=0;ii<100;ii++) {
            hashMap.put("attribute_testing_for_a_long_value_" + ii, ii+"");
        }
        return hashMap;
    }

    private static String randomValue(int base) {
        return Math.round(Math.random()*base)+"";
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
