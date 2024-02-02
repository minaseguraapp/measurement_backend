package co.minasegura.measurement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record Measurement(
    @NotNull Long timestamp,
    @NotNull @Valid MeasurementType measurementType,
    @NotNull @Valid Zone zone,
    @NotNull Map<String, String> measurementInfo) {}