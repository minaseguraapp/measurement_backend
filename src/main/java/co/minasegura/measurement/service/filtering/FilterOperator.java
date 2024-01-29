package co.minasegura.measurement.service.filtering;

import co.minasegura.measurement.model.Measurement;
import java.util.List;

public interface FilterOperator {

    List<Measurement> applyCriteria(List<Measurement> measurements, String value);
}
