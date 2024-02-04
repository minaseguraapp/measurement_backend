package co.minasegura.measurement.util;

import co.minasegura.measurement.properties.MeasurementProperties;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBUtil {

    private final MeasurementProperties properties;

    public DynamoDBUtil(MeasurementProperties properties) {
        this.properties = properties;
    }

    public Long getTimestamp(String measurementDateTimeType) {
        return Long.parseLong(measurementDateTimeType.split("#")[1]);
    }

    public String buildPartitionKey(String mine, String zoneId) {
        return String.format(properties.getPartitionKeyFormat(), getDefaultValue(mine),
            getDefaultValue(zoneId));
    }

    public String buildSortKey(String measurementType, Long timestamp) {
        return String.format(properties.getSortKeyFormat(), getDefaultValue(measurementType),
            getDefaultValue(timestamp));
    }

    public String getDefaultValue(Object value) {
        return Optional.ofNullable(value).map(Object::toString).filter(str -> !str.isBlank())
            .orElse("");
    }

}
