package co.minasegura.measurement.repository.impl;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.events.MeasurementEvent;
import co.minasegura.measurement.exception.NotValidParamException;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.properties.MeasurementProperties;
import co.minasegura.measurement.repository.IMeasurementRepository;
import co.minasegura.measurement.util.CommonsUtil;
import co.minasegura.measurement.util.DynamoDBUtil;
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
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Repository
public class MeasurementDynamoDBRepository implements IMeasurementRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    private final DynamoDBUtil dynamoUtil;
    private final DynamoDbEnhancedClient enhancedClient;
    private final SqsClient sqsClient;
    private final CommonsUtil commonsUtil;
    private final MeasurementProperties properties;


    public MeasurementDynamoDBRepository(
        DynamoDBUtil dynamoUtil,
        DynamoDbEnhancedClient enhancedClient,
        SqsClient sqsClient,
        CommonsUtil commonsUtil,
        MeasurementProperties properties
    ) {
        this.dynamoUtil = dynamoUtil;
        this.enhancedClient = enhancedClient;
        this.sqsClient = sqsClient;
        this.commonsUtil = commonsUtil;
        this.properties = properties;
    }

    public List<MeasurementEntity> getMeasurementEntities(String mineId, String zoneId,
        String measurementType) {
        LOGGER.info(
            "Get Measurement Repository Started with: MineID[{}] ZoneId[{}] MeasurementType[{}]",
            mineId, zoneId, measurementType);

        if (mineId == null || mineId.isBlank()) {
            throw new NotValidParamException("The mine id is empty");
        }
        DynamoDbTable<MeasurementEntity> measurementTable = enhancedClient.table("MeasurementTable",
            TableSchema.fromBean(MeasurementEntity.class));

        if (zoneId != null && !zoneId.isBlank()) {
            return queryTable(mineId, zoneId, measurementType, measurementTable);
        } else {
            return queryIndex(mineId, measurementType, measurementTable);
        }
    }

    private List<MeasurementEntity> queryTable(String mineId, String zoneId,
        String measurementType, DynamoDbTable<MeasurementEntity> measurementTable) {

        Key.Builder keyBuilder = Key.builder()
            .partitionValue(dynamoUtil.buildPartitionKey(mineId, zoneId));
        QueryConditional queryConditional = QueryConditional.keyEqualTo(keyBuilder.build());

        if (measurementType != null && !measurementType.isEmpty()) {
            keyBuilder.sortValue(String.format("%s#", measurementType));
            queryConditional = QueryConditional.sortBeginsWith(keyBuilder.build());
        }

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
            .queryConditional(queryConditional)
            .scanIndexForward(false)
            .build();

        return measurementTable.query(request)
            .stream()
            .flatMap(page -> page.items().stream())
            .collect(Collectors.toList());
    }

    private List<MeasurementEntity> queryIndex(String mineId, String measurementType,
        DynamoDbTable<MeasurementEntity> measurementTable) {
        Key.Builder keyBuilder = Key.builder().partitionValue(mineId);

        QueryConditional queryConditional;
        if (measurementType != null && !measurementType.isEmpty()) {
            queryConditional = QueryConditional.sortBeginsWith(
                keyBuilder.sortValue(measurementType).build());
        } else {
            queryConditional = QueryConditional.keyEqualTo(keyBuilder.build());
        }

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
            .queryConditional(queryConditional)
            .scanIndexForward(false)
            .build();

        DynamoDbIndex<MeasurementEntity> mineTypeIndex = measurementTable.index("MineTypeIndex");

        return mineTypeIndex.query(request)
            .stream()
            .flatMap(page -> page.items().stream()).toList();
    }

    @Override
    public boolean createMeasurement(MeasurementEntity measurement) {
        LOGGER.info("Creating New measurement Item");
        DynamoDbTable<MeasurementEntity> measurementTable = enhancedClient.table("MeasurementTable",
            TableSchema.fromBean(MeasurementEntity.class));

        measurementTable.putItem(measurement);
        return true;
    }

    @Override
    public boolean publishMeasurement(Measurement measurement) {
        LOGGER.info("Publishing New measurement Item, to be published on [{}]", properties.getQueueName());

        String queueUrl = sqsClient.getQueueUrl(
            builder -> builder.queueName(properties.getQueueName())).queueUrl();
        String messageBody = commonsUtil.toJson(
            new MeasurementEvent(properties.getEventTypeName(), measurement));

        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(messageBody)
            .build();

        SendMessageResponse response = sqsClient.sendMessage(sendMsgRequest);

        LOGGER.info("Event send to SQS queue [{}] [{}] with Response: [{}]", queueUrl, messageBody,
            response.toString());
        return true;
    }
}
