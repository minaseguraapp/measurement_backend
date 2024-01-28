package co.minasegura.measurement.service.imp;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.service.IMeasurementService;
import java.util.EnumMap;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService implements IMeasurementService {

    @Override
    public GetMeasurementResponse getMeasurements(EnumMap<MeasurementFilter, String> criteria) {
        return null;
    }
}
