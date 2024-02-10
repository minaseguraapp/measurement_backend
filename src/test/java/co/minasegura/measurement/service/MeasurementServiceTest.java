package co.minasegura.measurement.service;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.mapper.impl.MeasurementMapper;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.model.MeasurementType;
import co.minasegura.measurement.model.Mine;
import co.minasegura.measurement.model.Zone;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.repository.impl.MeasurementDynamoDBRepository;
import co.minasegura.measurement.service.impl.FilterCriteriaService;
import co.minasegura.measurement.service.impl.MeasurementService;
import co.minasegura.measurement.util.CommonsUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Any;

import java.util.EnumMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeasurementServiceTest {

    private MeasurementService measurementService;
    private IMeasurementRepository repository;
    private IFilterCriteriaService filterCriteriaService;
    private MeasurementMapper mapper;
    private CommonsUtil commonsUtil;

    @Before
    public void setUp() {
        repository = mock(IMeasurementRepository.class);
        filterCriteriaService = mock(IFilterCriteriaService.class);
        mapper = mock(MeasurementMapper.class);
        commonsUtil = mock(CommonsUtil.class);
        measurementService = new MeasurementService(repository, filterCriteriaService, mapper,
                commonsUtil);
    }

    @Test
    public void testGetMeasurements() {
        // Arrange
        EnumMap<MeasurementQueryFilter, String> criteria = new EnumMap<>(
                MeasurementQueryFilter.class);
        criteria.put(MeasurementQueryFilter.MINE, "mineId");
        criteria.put(MeasurementQueryFilter.ZONE_ID, "zoneId");
        criteria.put(MeasurementQueryFilter.MEASUREMENT_TYPE, "type");

        List<MeasurementEntity> measurementEntities = List.of(new MeasurementEntity());
        List<Measurement> measurements = List.of(new Measurement(123456L, MeasurementType.COAL_DUST,new Zone("23","Tambor",new Mine("123456")),null));

        when(repository.getMeasurementEntities("mineId", "zoneId", "type"))
                .thenReturn(measurementEntities);
        when(mapper.entityToModel(new MeasurementEntity())).thenReturn(new Measurement(123456L, MeasurementType.COAL_DUST,new Zone("23","Tambor",new Mine("123456")),null));
        when(filterCriteriaService.applyFiltering(any(), any())).thenReturn(measurements);

        // Act
        GetMeasurementResponse response = measurementService.getMeasurements(criteria);

        // Assert
        assertEquals(measurements.size(), response.measurements().size());
    }


    @Test
    public void testCreateMeasurement() {
        // Arrange
        Measurement measurementToRegister = new Measurement(123456L, MeasurementType.COAL_DUST,new Zone("23","Tambor",new Mine("123456")),null);
        MeasurementEntity entity = new MeasurementEntity();

        when(mapper.modelToEntity(measurementToRegister)).thenReturn(entity);
        when(repository.createMeasurement(entity)).thenReturn(true);

        // Act
        boolean result = measurementService.createMeasurement(measurementToRegister);

        // Assert
        assertTrue(result);
    }
}