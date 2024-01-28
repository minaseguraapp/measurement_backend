package co.minasegura.measurement.mapper;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.model.Measurement;

public interface IMeasurementMapper {

    Measurement entityToModel(MeasurementEntity entity);

    MeasurementEntity modelToEntity(Measurement entity);
}
