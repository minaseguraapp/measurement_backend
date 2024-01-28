package co.minasegura.measurement.util;

import co.minasegura.measurement.properties.MeasurementProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBUtil {

    private final MeasurementProperties properties;

    public DynamoDBUtil(MeasurementProperties properties) {
        this.properties = properties;
    }


    public Long getTimestamp(String measurementDateTimeType){
        return Long.parseLong(measurementDateTimeType.split("#")[1]);
    }

    public String buildPartitionKey(String mine, String zoneId){
        return String.format(properties.getPartitionKeyFormat(), mine, zoneId);
    }

    public String buildSortKey(String measurementType, Long timestamp){
        return String.format(properties.getSortKeyFormat(), measurementType, timestamp);
    }

}
