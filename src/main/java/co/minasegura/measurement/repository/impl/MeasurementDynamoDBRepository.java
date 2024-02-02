package co.minasegura.measurement.repository.impl;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.util.CommonsUtil;
import co.minasegura.measurement.util.DynamoDBUtil;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
public class MeasurementDynamoDBRepository implements IMeasurementRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final DynamoDBUtil dynamoUtil;
    private final CommonsUtil commonsUtil;
    private final DynamoDbEnhancedClient enhancedClient;


    public MeasurementDynamoDBRepository(DynamoDBUtil dynamoUtil, CommonsUtil commonsUtil, DynamoDbEnhancedClient enhancedClient) {
        this.dynamoUtil = dynamoUtil;
        this.commonsUtil = commonsUtil;
        this.enhancedClient= enhancedClient;
    }

    @Override
    public List<MeasurementEntity> getMeasurementEntities(String mineId, String zoneId, String measurementType) {
        LOGGER.info("Get Measurement Repository Started with: MineID[{}] ZoneId[{}] MeasurementType[{}]", mineId, zoneId, measurementType);

        String mineZoneID = dynamoUtil.buildPartitionKey(mineId, zoneId);

        DynamoDbTable<MeasurementEntity> measurementTable = enhancedClient.table("MeasurementTable", TableSchema.fromBean(MeasurementEntity.class));

        Key.Builder keyBuilder = Key.builder().partitionValue(mineZoneID);
        QueryConditional queryConditional = QueryConditional.keyEqualTo(keyBuilder.build());

        if (measurementType != null && !measurementType.isEmpty()) {
            String measurementDateTimeType = String.format("%s#", measurementType);
            keyBuilder.sortValue(measurementDateTimeType);
            queryConditional = QueryConditional.sortBeginsWith(keyBuilder.build());
        }

        return measurementTable.query(queryConditional).items().stream().collect(Collectors.toList());
    }

    @Override
    public boolean createMeasurement(MeasurementEntity measurement) {
        LOGGER.info("POST Measurement Repository");
        DynamoDbTable<MeasurementEntity> measurementTable = enhancedClient.table("MeasurementTable", TableSchema.fromBean(MeasurementEntity.class));

        measurementTable.putItem(measurement);
        return true;
    }
}
