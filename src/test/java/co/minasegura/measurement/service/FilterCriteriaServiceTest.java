package co.minasegura.measurement.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import co.minasegura.measurement.dto.MeasurementQueryFilter;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.model.MeasurementType;
import co.minasegura.measurement.service.FilterOperator;
import java.util.EnumMap;
import java.util.List;

import co.minasegura.measurement.service.impl.FilterCriteriaService;
import co.minasegura.measurement.service.impl.filter.ZoneTypeFilterOperator;
import org.junit.Before;
import org.junit.Test;

public class FilterCriteriaServiceTest {

    private FilterCriteriaService filterCriteriaService;
    private EnumMap<MeasurementQueryFilter, FilterOperator> filterOperator;

    @Before
    public void setUp() {
        filterOperator = new EnumMap<>(MeasurementQueryFilter.class);
        filterCriteriaService = new FilterCriteriaService(filterOperator);
    }

    @Test
    public void testApplyFiltering() {
        // Arrange
        List<Measurement> measurements = List.of(
                new Measurement(1L, MeasurementType.COAL_DUST, null, null),
                new Measurement(2L, MeasurementType.COAL_DUST, null, null),
                new Measurement(3L, MeasurementType.METHANE, null, null)
        );

        EnumMap<MeasurementQueryFilter, String> criteria = new EnumMap<>(MeasurementQueryFilter.class);
        criteria.put(MeasurementQueryFilter.MEASUREMENT_TYPE, "COAL_DUST");

        FilterOperator filterOperatorMock = mock(FilterOperator.class);
        when(filterOperatorMock.applyCriteria(any(), any()))
                .thenReturn(List.of(measurements.get(0), measurements.get(1)));

        filterOperator.put(MeasurementQueryFilter.MEASUREMENT_TYPE, filterOperatorMock);

        // Act
        List<Measurement> filteredMeasurements = filterCriteriaService.applyFiltering(measurements, criteria);

        // Assert
        assertEquals(2, filteredMeasurements.size());
    }
}
