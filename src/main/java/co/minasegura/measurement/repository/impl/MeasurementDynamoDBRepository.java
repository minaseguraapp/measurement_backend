package co.minasegura.measurement.repository.impl;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.util.DynamoDBUtil;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MeasurementDynamoDBRepository implements IMeasurementRepository {

    private final DynamoDBUtil dynamoUtil;
    private final DynamoDBMapper mapper;

    public MeasurementDynamoDBRepository(DynamoDBUtil dynamoUtil, DynamoDBMapper mapper) {
        this.dynamoUtil = dynamoUtil;
        this.mapper = mapper;
    }

    @Override
    public List<MeasurementEntity> getMeasurementEntities(String mineId, String zoneId,
        String measurementType) {
        String mineZoneID = dynamoUtil.buildPartitionKey(mineId, zoneId);
        String measurementDateTimeType = String.format("%s#", measurementType);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":mineZoneId", new AttributeValue().withS(mineZoneID));
        eav.put(":prefix", new AttributeValue().withS(measurementDateTimeType));

        DynamoDBQueryExpression<MeasurementEntity> queryExpression = new DynamoDBQueryExpression<MeasurementEntity>()
            .withKeyConditionExpression(
                "MineZoneID = :mineZoneId and begins_with(MeasurementDateTimeType, :prefix)")
            .withExpressionAttributeValues(eav);

        return mapper.query(MeasurementEntity.class, queryExpression);
    }
}
