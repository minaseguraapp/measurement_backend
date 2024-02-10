package co.minasegura.measurement.util;

import co.minasegura.measurement.properties.MeasurementProperties;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DynamoDBUtilTest {

    private DynamoDBUtil dynamoDBUtil;
    private MeasurementProperties properties;

    @Before
    public void setUp() {
        properties = mock(MeasurementProperties.class);
        when(properties.getPartitionKeyFormat()).thenReturn("Mine#%s#Zone#%s");
        when(properties.getSortKeyFormat()).thenReturn("%s#%s");
        dynamoDBUtil = new DynamoDBUtil(properties);
    }

    @Test
    public void testGetTimestamp() {
        // Arrange
        String measurementDateTimeType = "COAL_DUST#123456789";
        Long expectedTimestamp = 123456789L;

        // Act
        Long actualTimestamp = dynamoDBUtil.getTimestamp(measurementDateTimeType);

        // Assert
        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void testBuildPartitionKey() {
        // Arrange
        String mine = "123456";
        String zoneId = "23";
        String expectedPartitionKey = "Mine#123456#Zone#23";

        // Act
        String actualPartitionKey = dynamoDBUtil.buildPartitionKey(mine, zoneId);

        // Assert
        assertEquals(expectedPartitionKey, actualPartitionKey);
    }

    @Test
    public void testBuildSortKey() {
        // Arrange
        String measurementType = "COAL_DUST";
        Long timestamp = 123456789L;
        String expectedSortKey = "COAL_DUST#123456789";

        // Act
        String actualSortKey = dynamoDBUtil.buildSortKey(measurementType, timestamp);

        // Assert
        assertEquals(expectedSortKey, actualSortKey);
    }

    @Test
    public void testGetDefaultValue() {
        // Arrange
        Long  value = 123456L;
        String expectedDefaultValue = "123456";

        // Act
        String actualDefaultValue = dynamoDBUtil.getDefaultValue(value);

        // Assert
        assertEquals(expectedDefaultValue, actualDefaultValue);
    }
}