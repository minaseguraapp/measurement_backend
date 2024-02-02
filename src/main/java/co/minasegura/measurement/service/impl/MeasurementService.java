package co.minasegura.measurement.service.impl;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.mapper.impl.MeasurementMapper;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.service.IMeasurementService;
import co.minasegura.measurement.service.IFilterCriteriaService;
import co.minasegura.measurement.util.CommonsUtil;
import co.minasegura.measurement.util.ServiceUtil;
import jakarta.annotation.Nonnull;
import java.util.EnumMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class MeasurementService implements IMeasurementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final IMeasurementRepository repository;
    private final IFilterCriteriaService filterCriteriaService;
    private final ServiceUtil serviceUtil;
    private final MeasurementMapper mapper;
    private final CommonsUtil commonsUtil;

    public MeasurementService(IMeasurementRepository repository,
        IFilterCriteriaService filterCriteriaService, ServiceUtil serviceUtil,
        MeasurementMapper mapper,
        CommonsUtil commonsUtil) {
        this.repository = repository;
        this.filterCriteriaService = filterCriteriaService;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public GetMeasurementResponse getMeasurements(EnumMap<MeasurementFilter, String> criteria) {

        LOGGER.info("Get Measurement Service Started with: [{}]", commonsUtil.toJson(criteria));

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

    @Override
    public boolean createMeasurement(Measurement measurementToRegister){
        LOGGER.info("Get Measurement Service Started with: [{}]", commonsUtil.toJson(measurementToRegister));

        final MeasurementEntity entity= mapper.modelToEntity(measurementToRegister);

        return this.repository.createMeasurement(entity);
    }

    private List<Measurement> findMeasurementInDatabase(@Nonnull String mineId, String zoneId, String measurementType) {
        return this.repository.getMeasurementEntities(mineId, zoneId, measurementType).stream()
            .map(mapper::entityToModel).toList();
    }

}
