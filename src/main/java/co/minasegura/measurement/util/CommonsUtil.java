package co.minasegura.measurement.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommonsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final ObjectMapper mapper;

    public CommonsUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            LOGGER.error("Error converting object to JSON: ", e);
            return null;
        }
    }

    public <T> T toObject(String body, Class<T> clazz) {
        try {
            return mapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
