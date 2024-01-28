package co.minasegura.measurement.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import java.util.Map;

@DynamoDBTable(tableName = "MeasurementTable")
public class MeasurementEntity {
    private String mineZoneID;
    private String measurementDateTimeType;
    private MineEntity mine;
    private ZoneEntity zone;
    private Map<String, Object> measurementInfo;
    private String measurementType;

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
}