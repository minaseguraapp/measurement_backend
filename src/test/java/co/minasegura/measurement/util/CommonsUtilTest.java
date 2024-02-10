package co.minasegura.measurement.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommonsUtilTest {

    private CommonsUtil commonsUtil;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = mock(ObjectMapper.class);
        commonsUtil = new CommonsUtil(objectMapper);
    }

    @Test
    public void testToJson() throws JsonProcessingException {
        // Arrange
        Object value = new Object();
        String expectedJson = "{\"key\":\"value\"}";
        when(objectMapper.writeValueAsString(value)).thenReturn(expectedJson);

        // Act
        String actualJson = commonsUtil.toJson(value);

        // Assert
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testToObject() throws JsonProcessingException {
        // Arrange
        String json = "{\"key\":\"value\"}";
        Object expectedObject = new Object();
        when(objectMapper.readValue(json, Object.class)).thenReturn(expectedObject);

        // Act
        Object actualObject = commonsUtil.toObject(json, Object.class);

        // Assert
        assertEquals(expectedObject, actualObject);
    }
}