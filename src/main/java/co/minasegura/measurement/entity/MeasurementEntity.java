package co.minasegura.measurement.entity;

import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class MeasurementEntity {

    private String mineZoneId;
    private String measurementTypeTimestamp;
    private String mineId;
    private String zoneId;
    private Long timestamp;
    private String measurementType;
    private MineEntity mine;
    private ZoneEntity zone;
    private Map<String, String> measurementInfo;

    public MeasurementEntity() {
    }

    public MeasurementEntity(String mineZoneId, String measurementTypeTimestamp, String mineId,
        String zoneId, Long timestamp, String measurementType, MineEntity mine, ZoneEntity zone,
        Map<String, String> measurementInfo) {
        this.mineZoneId = mineZoneId;
        this.measurementTypeTimestamp = measurementTypeTimestamp;
        this.mineId = mineId;
        this.zoneId = zoneId;
        this.timestamp = timestamp;
        this.measurementType = measurementType;
        this.mine = mine;
        this.zone = zone;
        this.measurementInfo = measurementInfo;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("mineZoneId")
    public String getMineZoneId() {
        return mineZoneId;
    }

    public void setMineZoneId(String mineZoneId) {
        this.mineZoneId = mineZoneId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("measurementTypeTimestamp")
    @DynamoDbSecondarySortKey(indexNames = "MineTypeIndex")
    public String getMeasurementTypeTimestamp() {
        return measurementTypeTimestamp;
    }

    public void setMeasurementTypeTimestamp(String measurementTypeTimestamp) {
        this.measurementTypeTimestamp = measurementTypeTimestamp;
    }

    @DynamoDbAttribute("mineId")
    @DynamoDbSecondaryPartitionKey(indexNames = "MineTypeIndex")
    public String getMineId() {
        return mineId;
    }

    public void setMineId(String mineId) {
        this.mineId = mineId;
    }

    @DynamoDbAttribute("zoneId")
    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    @DynamoDbAttribute("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("measurementType")
    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @DynamoDbAttribute("mine")
    public MineEntity getMine() {
        return mine;
    }

    public void setMine(MineEntity mine) {
        this.mine = mine;
    }

    @DynamoDbAttribute("zone")
    public ZoneEntity getZone() {
        return zone;
    }

    public void setZone(ZoneEntity zone) {
        this.zone = zone;
    }

    @DynamoDbAttribute("measurementInfo")
    public Map<String, String> getMeasurementInfo() {
        return measurementInfo;
    }

    public void setMeasurementInfo(Map<String, String> measurementInfo) {
        this.measurementInfo = measurementInfo;
    }
}
