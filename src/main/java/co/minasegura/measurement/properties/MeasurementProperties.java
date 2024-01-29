package co.minasegura.measurement.properties;

import co.minasegura.measurement.dto.MeasurementFilter;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "measurement")
public class MeasurementProperties{

    private Set<MeasurementFilter> requiredFilters;
    private String partitionKeyFormat;
    private String sortKeyFormat;

    public Set<MeasurementFilter> getRequiredFilters() {
        return requiredFilters;
    }

    public String getPartitionKeyFormat() {
        return partitionKeyFormat;
    }

    public String getSortKeyFormat() {
        return sortKeyFormat;
    }

    public void setRequiredFilters(
        Set<MeasurementFilter> requiredFilters) {
        this.requiredFilters = requiredFilters;
    }

    public void setPartitionKeyFormat(String partitionKeyFormat) {
        this.partitionKeyFormat = partitionKeyFormat;
    }

    public void setSortKeyFormat(String sortKeyFormat) {
        this.sortKeyFormat = sortKeyFormat;
    }
}
