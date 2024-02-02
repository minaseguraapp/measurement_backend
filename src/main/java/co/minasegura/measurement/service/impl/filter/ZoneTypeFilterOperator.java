package co.minasegura.measurement.service.impl.filter;

import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.service.FilterOperator;
import java.util.List;

public class ZoneTypeFilterOperator implements FilterOperator {

    @Override
    public List<Measurement> applyCriteria(List<Measurement> measurements, String value) {
        return measurements.stream()
            .filter(measurement -> measurement.zone() != null && value.equals(measurement.zone().type()))
            .toList();
    }
}
