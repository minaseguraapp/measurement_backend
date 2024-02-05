package co.minasegura.measurement.service;


import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.model.Measurement;
import java.util.EnumMap;

public interface IMeasurementService {

    GetMeasurementResponse getMeasurements(EnumMap<MeasurementQueryFilter, String> criteria);

    boolean createMeasurement(Measurement measurementToRegister);

}
