package co.minasegura.measurement.service.filtering;

import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.model.Measurement;
import java.util.EnumMap;
import java.util.List;

public interface IFilterCriteriaService {

    List<Measurement> applyFiltering(List<Measurement> measurements, EnumMap<MeasurementFilter, String> criteria);
}
