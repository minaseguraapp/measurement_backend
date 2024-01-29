package co.minasegura.measurement.service.impl.filtering;

import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.service.filtering.FilterOperator;
import co.minasegura.measurement.service.filtering.IFilterCriteriaService;
import java.util.EnumMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
class FilterCriteriaService implements IFilterCriteriaService {

    private final EnumMap<MeasurementFilter, FilterOperator> filterOperator;

    public FilterCriteriaService(EnumMap<MeasurementFilter, FilterOperator> filterOperator) {
        this.filterOperator = filterOperator;
    }

    @Override
    public List<Measurement> applyFiltering(List<Measurement> measurements,
        EnumMap<MeasurementFilter, String> criteria) {

        List<Measurement> tempMeasurements = measurements;

        for (var criteriaEntry : criteria.entrySet()) {
            if (filterOperator.containsKey(criteriaEntry.getKey())) {
                FilterOperator operator = filterOperator.get(criteriaEntry.getKey());
                tempMeasurements = operator.applyCriteria(tempMeasurements,
                    criteriaEntry.getValue());
            }
        }
        return measurements;
    }

}
