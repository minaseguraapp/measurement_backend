package co.minasegura.measurement.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import java.util.Map;

@DynamoDbBean
public class MeasurementEntity {
    private String mineZoneID;
    private String measurementDateTimeType;
    private MineEntity mine;
    private ZoneEntity zone;
    private Map<String, String> measurementInfo;
    private String measurementType;
    private Long timestamp;

    public MeasurementEntity() {
    }

    public MeasurementEntity(String mineZoneID, String measurementDateTimeType, MineEntity mine,
        ZoneEntity zone, Map<String, String> measurementInfo, String measurementType,
        Long timestamp) {
        this.mineZoneID = mineZoneID;
        this.measurementDateTimeType = measurementDateTimeType;
        this.mine = mine;
        this.zone = zone;
        this.measurementInfo = measurementInfo;
        this.measurementType = measurementType;
        this.timestamp = timestamp;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("MineZoneID")
    public String getMineZoneID() {
        return mineZoneID;
    }

    public void setMineZoneID(String mineZoneID) {
        this.mineZoneID = mineZoneID;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("MeasurementDateTimeType")
    public String getMeasurementDateTimeType() {
        return measurementDateTimeType;
    }

    public void setMeasurementDateTimeType(String measurementDateTimeType) {
        this.measurementDateTimeType = measurementDateTimeType;
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

    @DynamoDbAttribute("measurementType")
    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @DynamoDbAttribute("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
