package co.minasegura.measurement.properties;

import co.minasegura.measurement.dto.MeasurementQueryFilter;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "measurement")
public class MeasurementProperties{

    private Set<MeasurementQueryFilter> requiredFilters;
    private String partitionKeyFormat;
    private String sortKeyFormat;
    private String queueName;
    private String eventTypeName;

    public Set<MeasurementQueryFilter> getRequiredFilters() {
        return requiredFilters;
    }

    public String getPartitionKeyFormat() {
        return partitionKeyFormat;
    }

    public String getSortKeyFormat() {
        return sortKeyFormat;
    }

    public void setRequiredFilters(
        Set<MeasurementQueryFilter> requiredFilters) {
        this.requiredFilters = requiredFilters;
    }

    public void setPartitionKeyFormat(String partitionKeyFormat) {
        this.partitionKeyFormat = partitionKeyFormat;
    }

    public void setSortKeyFormat(String sortKeyFormat) {
        this.sortKeyFormat = sortKeyFormat;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
