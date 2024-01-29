package co.minasegura.measurement.service.impl;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.mapper.impl.MeasurementMapper;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.service.IMeasurementService;
import co.minasegura.measurement.service.filtering.IFilterCriteriaService;
import co.minasegura.measurement.util.ServiceUtil;
import jakarta.annotation.Nonnull;
import java.util.EnumMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
class MeasurementService implements IMeasurementService {

    private final IMeasurementRepository repository;
    private final IFilterCriteriaService filterCriteriaService;
    private final ServiceUtil serviceUtil;
    private final MeasurementMapper mapper;

    MeasurementService(IMeasurementRepository repository, ServiceUtil serviceUtil,
        IFilterCriteriaService filterCriteriaService, MeasurementMapper mapper) {
        this.repository = repository;
        this.filterCriteriaService = filterCriteriaService;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
    }

    @Override
    public GetMeasurementResponse getMeasurements(EnumMap<MeasurementFilter, String> criteria) {
        final String mineId = serviceUtil.extractFilterParam(criteria, MeasurementFilter.MINE,
            true);
        final String zoneId = serviceUtil.extractFilterParam(criteria, MeasurementFilter.ZONE_ID,
            true);
        final String measurementType = serviceUtil.extractFilterParam(criteria,
            MeasurementFilter.MEASUREMENT_TYPE, false);

        List<Measurement> measurements = findMeasurementInDatabase(mineId, zoneId, measurementType);

        measurements = filterCriteriaService.applyFiltering(measurements, criteria);

        return new GetMeasurementResponse(measurements);
    }


    private List<Measurement> findMeasurementInDatabase(@Nonnull String mineId,
        @Nonnull String zoneId, String measurementType) {
        return this.repository.getMeasurementEntities(mineId, zoneId, measurementType).stream()
            .map(mapper::entityToModel).toList();
    }


}
