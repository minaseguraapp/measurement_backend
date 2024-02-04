package co.minasegura.measurement.repository.impl;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.exception.NotValidParamException;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.util.CommonsUtil;
import co.minasegura.measurement.util.DynamoDBUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

@Repository
public class MeasurementDynamoDBRepository implements IMeasurementRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final DynamoDBUtil dynamoUtil;
    private final DynamoDbEnhancedClient enhancedClient;


    public MeasurementDynamoDBRepository(DynamoDBUtil dynamoUtil,
        DynamoDbEnhancedClient enhancedClient) {
        this.dynamoUtil = dynamoUtil;
        this.enhancedClient = enhancedClient;
    }

    @Override
    public List<MeasurementEntity> getMeasurementEntities(String mineId, String zoneId,
        String measurementType) {
        LOGGER.info(
            "Get Measurement Repository Started with: MineID[{}] ZoneId[{}] MeasurementType[{}]",
            mineId, zoneId, measurementType);

        if (mineId == null || mineId.isBlank()) {
            throw new NotValidParamException("The mine id is empty");
        }

        final DynamoDbTable<MeasurementEntity> measurementTable = enhancedClient.table(
            "MeasurementTable", TableSchema.fromBean(MeasurementEntity.class));

        if (zoneId != null && !zoneId.isBlank()) {
            Key.Builder keyBuilder = Key.builder()
                .partitionValue(dynamoUtil.buildPartitionKey(mineId, zoneId));

            QueryConditional queryConditional = QueryConditional.keyEqualTo(keyBuilder.build());

            if (measurementType != null && !measurementType.isEmpty()) {
                keyBuilder.sortValue(String.format("%s#", measurementType));
                queryConditional = QueryConditional.sortBeginsWith(keyBuilder.build());
            }

            return measurementTable.query(queryConditional).items().stream()
                .collect(Collectors.toList());
        } else {
            Key.Builder keyBuilder = Key.builder().partitionValue(mineId);

            if (measurementType != null && !measurementType.isEmpty()) {
                keyBuilder.sortValue(measurementType);
            }

            final QueryConditional queryConditional = measurementType != null ?
                QueryConditional.sortBeginsWith(keyBuilder.build()) : QueryConditional
                .keyEqualTo(keyBuilder.build()) ;

            final DynamoDbIndex<MeasurementEntity> mineTypeIndex = measurementTable.index(
                "MineTypeIndex");

            final List<MeasurementEntity> results = new ArrayList<>();

            mineTypeIndex.query(r -> r.queryConditional(queryConditional)).stream()
                .forEach(page -> results.addAll(page.items()));

            return results;
        }
    }

    @Override
    public boolean createMeasurement(MeasurementEntity measurement) {
        LOGGER.info("POST Measurement Repository");
        DynamoDbTable<MeasurementEntity> measurementTable = enhancedClient.table("MeasurementTable",
            TableSchema.fromBean(MeasurementEntity.class));

        measurementTable.putItem(measurement);
        return true;
    }
}
