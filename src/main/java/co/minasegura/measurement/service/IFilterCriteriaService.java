package co.minasegura.measurement.service;

import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.model.Measurement;
import java.util.EnumMap;
import java.util.List;

public interface IFilterCriteriaService {

    List<Measurement> applyFiltering(List<Measurement> measurements, EnumMap<MeasurementQueryFilter, String> criteria);
}
