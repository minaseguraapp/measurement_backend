package co.minasegura.measurement.dto;

import java.util.List;

public record GetMeasurementResponse(List<MeasurementInfoDTO> measurements) {
}
