package co.minasegura.measurement.service.impl;

import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.service.FilterOperator;
import co.minasegura.measurement.service.IFilterCriteriaService;
import java.util.EnumMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FilterCriteriaService implements IFilterCriteriaService {

    private final EnumMap<MeasurementQueryFilter, FilterOperator> filterOperator;

    public FilterCriteriaService(EnumMap<MeasurementQueryFilter, FilterOperator> filterOperator) {
        this.filterOperator = filterOperator;
    }

    @Override
    public List<Measurement> applyFiltering(List<Measurement> measurements,
        EnumMap<MeasurementQueryFilter, String> criteria) {

        List<Measurement> tempMeasurements = measurements;

        for (var criteriaEntry : criteria.entrySet()) {
            if (filterOperator.containsKey(criteriaEntry.getKey())) {
                FilterOperator operator = filterOperator.get(criteriaEntry.getKey());
                tempMeasurements = operator.applyCriteria(tempMeasurements,
                    criteriaEntry.getValue());
            }
        }
        return tempMeasurements;
    }

}
