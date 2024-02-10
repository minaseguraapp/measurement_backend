package co.minasegura.measurement.mapper;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.entity.MineEntity;
import co.minasegura.measurement.entity.ZoneEntity;
import co.minasegura.measurement.mapper.impl.MeasurementMapper;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.model.MeasurementType;
import co.minasegura.measurement.model.Mine;
import co.minasegura.measurement.model.Zone;
import co.minasegura.measurement.util.DynamoDBUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MeasurementMapperTest {

    private MeasurementMapper measurementMapper;
    private DynamoDBUtil dbUtil;

    @Before
    public void setUp() {
        dbUtil = Mockito.mock(DynamoDBUtil.class);
        measurementMapper = new MeasurementMapper(dbUtil);
    }

    @Test
    public void testEntityToModel() {
        // Arrange
        MeasurementEntity entity = new MeasurementEntity(
                "Mine#123456#Zone#23",
                "COAL_DUST#123456789",
                "123456",
                "23",
                123456789L,
                "COAL_DUST",
                new MineEntity("123456"),
                new ZoneEntity("23", "TAMBOR"),
                new HashMap<>()
        );

        when(dbUtil.getTimestamp("COAL_DUST#123456789")).thenReturn(123456789L);

        // Act
        Measurement measurement = measurementMapper.entityToModel(entity);

        // Assert
        assertEquals("123456", measurement.zone().mine().id());
        assertEquals("23", measurement.zone().id());
        assertEquals("TAMBOR", measurement.zone().type());
        assertEquals(123456789L, measurement.timestamp().longValue());
        assertEquals(MeasurementType.COAL_DUST, measurement.measurementType());
    }

    @Test
    public void testModelToEntity() {
        // Arrange
        Measurement model = new Measurement(
                123456789L,
                MeasurementType.COAL_DUST,
                new Zone("23", "TAMBOR", new Mine("123456")),
                new HashMap<>()
        );

        when(dbUtil.buildPartitionKey("123456", "23")).thenReturn("Mine#123456#Zone#23");
        when(dbUtil.buildSortKey("COAL_DUST", 123456789L)).thenReturn("COAL_DUST#123456789");

        // Act
        MeasurementEntity entity = measurementMapper.modelToEntity(model);

        // Assert
        assertEquals("Mine#123456#Zone#23", entity.getMineZoneId());
        assertEquals("COAL_DUST#123456789", entity.getMeasurementTypeTimestamp());
        assertEquals("123456", entity.getMine().getId());
        assertEquals("23", entity.getZone().getId());
        assertEquals(123456789L, Long.parseLong(entity.getMeasurementTypeTimestamp().split("#")[1]));
        assertEquals("COAL_DUST", entity.getMeasurementType());
    }
}