package co.minasegura.measurement.repository;

import co.minasegura.measurement.entity.MeasurementEntity;
import jakarta.annotation.Nonnull;
import java.util.List;

public interface IMeasurementRepository {

    List<MeasurementEntity> getMeasurementEntities(@Nonnull String mineId, @Nonnull String ZoneId,
        String measurementType);

    boolean createMeasurement(MeasurementEntity measurement);
}
