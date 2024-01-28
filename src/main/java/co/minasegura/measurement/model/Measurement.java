package co.minasegura.measurement.model;

import java.util.Map;

public record Measurement(Long timestamp, MeasurementType measurementType, Zone zone, Map<String, Object> measurementInfo) {}
