package co.minasegura.measurement.util;

import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.properties.MeasurementProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class EntrypointUtil {

    private final MeasurementProperties properties;
    private final ObjectMapper mapper;


    public EntrypointUtil(MeasurementProperties properties, ObjectMapper mapper) {
        this.properties = properties;
        this.mapper = mapper;
    }

    public EnumMap<MeasurementFilter, String> getMeasurementFilter(
        Map<String, String> queryParams) {

        final Set<String> filterSet = Stream
            .of(MeasurementFilter.values())
            .map(MeasurementFilter::getFilter).collect(
                Collectors.toSet());
        final EnumMap<MeasurementFilter, String> filters = new EnumMap<>(MeasurementFilter.class);

        queryParams.entrySet().stream()
            .filter(entry -> filterSet.contains(entry.getKey()))
            .forEach(
                entry -> filters.put(MeasurementFilter.valueOf(entry.getKey()), entry.getValue()));

        return filters;
    }

    public boolean hasRequestMinimumCriteria(EnumMap<MeasurementFilter, String> searchCriteria) {
        return properties.getRequiredFilters().stream().allMatch(searchCriteria::containsKey);
    }

    public String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            System.err.println("Error converting object to JSON: " + e.getMessage());
            return null;
        }
    }
}
