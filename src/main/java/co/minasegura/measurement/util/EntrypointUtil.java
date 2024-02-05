package co.minasegura.measurement.util;

import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.properties.MeasurementProperties;
import jakarta.validation.Validator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class EntrypointUtil {

    private final MeasurementProperties properties;
    private final Validator validator;

    public EntrypointUtil(MeasurementProperties properties, Validator validator) {
        this.properties = properties;
        this.validator= validator;
    }

    public EnumMap<MeasurementQueryFilter, String> getMeasurementFilter(
        Map<String, String> queryParams) {

        final Map<String, MeasurementQueryFilter> invertedFilterMap = Stream.of(
                MeasurementQueryFilter.values())
            .collect(Collectors.toMap(MeasurementQueryFilter::getFilter, filter -> filter));

        final EnumMap<MeasurementQueryFilter, String> filters = new EnumMap<>(
            MeasurementQueryFilter.class);

        queryParams.entrySet().stream()
            .filter(entry -> invertedFilterMap.containsKey(entry.getKey()))
            .forEach(entry -> filters.put(invertedFilterMap.get(entry.getKey()), entry.getValue()));

        return filters;
    }

    public boolean hasRequestMinimumCriteria(EnumMap<MeasurementQueryFilter, String> searchCriteria) {
        return properties.getRequiredFilters().stream().allMatch(searchCriteria::containsKey);
    }

    public boolean isMeasurementValid(Measurement measurement) {
        return measurement != null && validator.validate(measurement).isEmpty();
    }

}
