package co.minasegura.measurement.service;


import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.model.Measurement;
import java.util.EnumMap;

public interface IMeasurementService {

    GetMeasurementResponse getMeasurements(EnumMap<MeasurementFilter, String> criteria);

    boolean createMeasurement(Measurement measurementToRegister);

}
