package co.minasegura.measurement.util;

import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.exception.NotValidParamException;
import co.minasegura.measurement.properties.MeasurementExceptionProperties;
import java.util.EnumMap;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

    private final MeasurementExceptionProperties exceptionProperties;

    public ServiceUtil(MeasurementExceptionProperties exceptionProperties) {
        this.exceptionProperties = exceptionProperties;
    }

    public String extractFilterParam(EnumMap<MeasurementFilter, String> criteria,
        MeasurementFilter paramToSearch, boolean required) {
        String param = criteria.get(paramToSearch);
        if (param == null && required) {
            throw new NotValidParamException(exceptionProperties.getMineParamNotPresentMessage());
        }
        return param;
    }

}
