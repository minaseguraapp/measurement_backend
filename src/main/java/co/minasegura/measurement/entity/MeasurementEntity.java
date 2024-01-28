package co.minasegura.measurement.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.Map;

@DynamoDBTable(tableName = "MeasurementTable")
public class MeasurementEntity {

    private String mineZoneID;
    private String measurementDateTimeType;
    private MineEntity mine;
    private ZoneEntity zone;
    private Map<String, Object> measurementInfo;
    private String measurementType;
    private long timestamp;

    public MeasurementEntity(
        String mineZoneID,
        String measurementDateTimeType,
        MineEntity mine,
        ZoneEntity zone,
        Map<String, Object> measurementInfo,
        String measurementType,
        long timestamp
    ) {
        this.mineZoneID = mineZoneID;
        this.measurementDateTimeType = measurementDateTimeType;
        this.mine = mine;
        this.zone = zone;
        this.measurementInfo = measurementInfo;
        this.measurementType = measurementType;
        this.timestamp = timestamp;
    }

    @DynamoDBHashKey(attributeName = "MineZoneID")
    public String getMineZoneID() {
        return mineZoneID;
    }

    public void setMineZoneID(String mineZoneID) {
        this.mineZoneID = mineZoneID;
    }

    @DynamoDBRangeKey(attributeName = "MeasurementDateTimeType")
    public String getMeasurementDateTimeType() {
        return measurementDateTimeType;
    }

    public void setMeasurementDateTimeType(String measurementDateTimeType) {
        this.measurementDateTimeType = measurementDateTimeType;
    }

    @DynamoDBAttribute(attributeName = "mine")
    public MineEntity getMine() {
        return mine;
    }

    public void setMine(MineEntity mine) {
        this.mine = mine;
    }

    @DynamoDBAttribute(attributeName = "zone")
    public ZoneEntity getZone() {
        return zone;
    }

    public void setZone(ZoneEntity zone) {
        this.zone = zone;
    }

    @DynamoDBAttribute(attributeName = "measurementInfo")
    public Map<String, Object> getMeasurementInfo() {
        return measurementInfo;
    }

    public void setMeasurementInfo(Map<String, Object> measurementInfo) {
        this.measurementInfo = measurementInfo;
    }

    @DynamoDBAttribute(attributeName = "measurementType")
    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @DynamoDBAttribute(attributeName = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}