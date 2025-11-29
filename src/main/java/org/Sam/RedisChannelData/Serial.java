package org.Sam.RedisChannelData;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serial {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Serialize any record to JSON with message type information
     * @param record the record to serialize
     * @return the JSON string representation of the record with type information
     */
    public static String toJson(Record record) {
        try {
            // Create a wrapper object with type information using the record's class name
            MessageWrapper wrapper = new MessageWrapper(record.getClass().getName(), record);
            return MAPPER.writeValueAsString(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize record to JSON", e);
        }
    }


    /**
     * Deserialize JSON to the correct record type automatically
     * @param json the JSON string representation of the message
     * @return the deserialized record of the correct type
     */
    public static Record fromJson(String json) {
        try {
            JsonNode jsonNode = MAPPER.readTree(json);

            // Get the message type (class name)
            String className = jsonNode.get("messageType").asText();
            Class<?> recordClass = Class.forName(className);

            // Verify it's a record
            if (!recordClass.getSuperclass().equals(Record.class)) {
                throw new IllegalArgumentException("Class " + className + " is not a record");
            }

            // Get the actual message data
            JsonNode messageData = jsonNode.get("data");

            // Deserialize to the correct record type
            return (Record) MAPPER.treeToValue(messageData, recordClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to record", e);
        }
    }


    /**
     * Helper wrapper class for JSON structure with type information
     */
    private record MessageWrapper(String messageType, Record data) {}
}