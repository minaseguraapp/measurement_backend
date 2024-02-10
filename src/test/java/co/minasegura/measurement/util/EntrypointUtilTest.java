package co.minasegura.measurement.util;

import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.model.MeasurementType;
import co.minasegura.measurement.model.Mine;
import co.minasegura.measurement.model.Zone;
import co.minasegura.measurement.properties.MeasurementProperties;
import jakarta.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntrypointUtilTest {

    private EntrypointUtil entrypointUtil;
    private MeasurementProperties properties;
    private Validator validator;

    @Before
    public void setUp() {
        properties = mock(MeasurementProperties.class);
        validator = mock(Validator.class);
        entrypointUtil = new EntrypointUtil(properties, validator);
    }

    @Test
    public void testGetMeasurementFilter() {
        // Arrange
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("mine", "123456");
        queryParams.put("zoneId", "23");

        // Act
        EnumMap<MeasurementQueryFilter, String> measurementFilter = entrypointUtil
                .getMeasurementFilter(queryParams);

        // Assert
        assertEquals(2, measurementFilter.size());
        assertTrue(measurementFilter.containsKey(MeasurementQueryFilter.MINE));
        assertTrue(measurementFilter.containsKey(MeasurementQueryFilter.ZONE_ID));
        assertEquals("123456", measurementFilter.get(MeasurementQueryFilter.MINE));
        assertEquals("23", measurementFilter.get(MeasurementQueryFilter.ZONE_ID));
    }

    @Test
    public void testHasRequestMinimumCriteria() {
        // Arrange
        EnumMap<MeasurementQueryFilter, String> searchCriteria = new EnumMap<>(
                MeasurementQueryFilter.class);
        searchCriteria.put(MeasurementQueryFilter.MINE, "123456");
        searchCriteria.put(MeasurementQueryFilter.ZONE_ID, "23");

        when(properties.getRequiredFilters()).thenReturn(
                Set.of(MeasurementQueryFilter.MINE, MeasurementQueryFilter.ZONE_ID));

        // Act
        boolean result = entrypointUtil.hasRequestMinimumCriteria(searchCriteria);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsMeasurementValid() {
        // Arrange
        Measurement measurement = new Measurement(123456L, MeasurementType.COAL_DUST,new Zone("23","Tambor",new Mine("123456")),null);
        when(validator.validate(measurement)).thenReturn(Set.of());

        // Act
        boolean result = entrypointUtil.isMeasurementValid(measurement);

        // Assert
        assertTrue(result);
    }
}