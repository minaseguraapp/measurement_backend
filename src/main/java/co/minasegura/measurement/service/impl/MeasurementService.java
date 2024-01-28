package co.minasegura.measurement.service.impl;

import co.minasegura.measurement.dto.GetMeasurementResponse;
import co.minasegura.measurement.dto.MeasurementFilter;
import co.minasegura.measurement.exception.NotValidParamException;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.properties.MeasurementExceptionProperties;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.service.IMeasurementService;
import jakarta.annotation.Nonnull;
import java.util.EnumMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService implements IMeasurementService {

    private final IMeasurementRepository repository;
    private final MeasurementExceptionProperties exceptionProperties;

    MeasurementService(IMeasurementRepository repository, MeasurementExceptionProperties exceptionProperties){
        this.repository= repository;
        this.exceptionProperties=exceptionProperties;
    }

    @Override
    public GetMeasurementResponse getMeasurements(EnumMap<MeasurementFilter, String> criteria) {
        final String mineId=extractFilterParam(criteria, MeasurementFilter.MINE, true);
        final String zoneId=extractFilterParam(criteria, MeasurementFilter.ZONE_ID, true);
        final String measurementType=extractFilterParam(criteria, MeasurementFilter.MEASUREMENT_TYPE, false);

        List<Measurement> measurements= findMeasurementInDatabase(mineId, zoneId, measurementType);

        return null;
    }



    private List<Measurement> findMeasurementInDatabase(@Nonnull String mineId, @Nonnull String ZoneId, String measurementType){
        return null;
    }

    private String extractFilterParam(EnumMap<MeasurementFilter, String> criteria, MeasurementFilter paramToSearch, boolean required){
        if (!criteria.containsKey(paramToSearch)){
            if (required){
                throw new NotValidParamException(exceptionProperties.getMineParamNotPresentMessage());
            }
            else{
                return null;
            }
        }
        return criteria.get(MeasurementFilter.MINE);
    }

}
