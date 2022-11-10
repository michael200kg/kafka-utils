package com.michael200kg.test.simpleproducer.utils;

import com.google.common.io.Resources;

import java.io.IOException;

import static org.apache.commons.compress.utils.Charsets.UTF_8;

public class KafkaUtils {
    public static String getSchemaFromFile(String filename) throws IOException {
        return Resources.toString(Resources.getResource(filename), UTF_8);
    }
}
