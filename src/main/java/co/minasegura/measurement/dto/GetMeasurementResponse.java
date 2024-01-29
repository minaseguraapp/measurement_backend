package co.minasegura.measurement.dto;

import co.minasegura.measurement.model.Measurement;
import java.util.List;

public record GetMeasurementResponse(List<Measurement> measurements) {

}
