package co.minasegura.measurement.service.impl;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.mapper.impl.MeasurementMapper;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.service.IFilterCriteriaService;
import co.minasegura.measurement.service.IMeasurementService;
import co.minasegura.measurement.util.CommonsUtil;
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
    private final MeasurementMapper mapper;
    private final CommonsUtil commonsUtil;

    public MeasurementService(IMeasurementRepository repository,
        IFilterCriteriaService filterCriteriaService,
        MeasurementMapper mapper,
        CommonsUtil commonsUtil) {
        this.repository = repository;
        this.filterCriteriaService = filterCriteriaService;
        this.mapper = mapper;
        this.commonsUtil = commonsUtil;
    }

    @Override
    public GetMeasurementResponse getMeasurements(
        EnumMap<MeasurementQueryFilter, String> criteria) {

        LOGGER.info("Get Measurement Service Started with: [{}]", commonsUtil.toJson(criteria));

        List<Measurement> measurements = findMeasurementInDatabase(
            criteria.get(MeasurementQueryFilter.MINE),
            criteria.get(MeasurementQueryFilter.ZONE_ID),
            criteria.get(MeasurementQueryFilter.MEASUREMENT_TYPE));

        measurements = filterCriteriaService.applyFiltering(measurements, criteria);

        return new GetMeasurementResponse(measurements);
    }

    @Override
    public boolean createMeasurement(Measurement measurementToRegister) {
        LOGGER.info("Get Measurement Service Started with: [{}]",
            commonsUtil.toJson(measurementToRegister));

        final MeasurementEntity entity = mapper.modelToEntity(measurementToRegister);
        this.repository.createMeasurement(entity);
        this.repository.publishMeasurement(measurementToRegister);
        return true;
    }

    private List<Measurement> findMeasurementInDatabase(String mineId, String zoneId,
        String measurementType) {
        return this.repository.getMeasurementEntities(mineId, zoneId, measurementType).stream()
            .map(mapper::entityToModel).toList();
    }

}
