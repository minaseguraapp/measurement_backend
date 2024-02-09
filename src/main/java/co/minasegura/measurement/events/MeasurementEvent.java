package co.minasegura.measurement.events;

import co.minasegura.measurement.model.Measurement;

public record MeasurementEvent(String eventType, Measurement measurement) {

}
